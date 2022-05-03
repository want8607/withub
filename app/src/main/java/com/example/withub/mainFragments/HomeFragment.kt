package com.example.withub.mainFragments

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.withub.DrawerActivity
import com.example.withub.FriendActivity
import com.example.withub.dataclasses.ChartData
import com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainFragmentAdapters.HomePagerRecyclerAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.RollingTextView
import com.yy.mobile.rollingtextview.strategy.Strategy
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(){

    lateinit var mainActivity: MainActivity
    lateinit var pagerRecyclerView: ViewPager2
    lateinit var lineChart: LineChart
    var intervalTime = 3000.toLong()
    var bannerPosition = (Int.MAX_VALUE/2)+1
    var numBanner = 4
    var homeHandler = HomeHandler()
    val chartDatas = ArrayList<ChartData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.home_fragment,container,false)
        mainActivity = activity as MainActivity

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //nav버튼
        val navButton = view.findViewById<ImageButton>(R.id.nav_button)
        navButton.setOnClickListener {
            val intent = Intent(mainActivity, DrawerActivity::class.java)
            startActivity(intent)
            mainActivity.overridePendingTransition(R.anim.lefttoright_animation, R.anim.hold)

        }

        //topView 설정
        val rollingTextView = view.findViewById<RollingTextView>(R.id.rolling_commit)
        rollingTextView.animationDuration = 1000L
        rollingTextView.charStrategy = Strategy.NormalAnimation()
        rollingTextView.addCharOrder(CharOrder.Number)
        rollingTextView.animationInterpolator = AccelerateDecelerateInterpolator()
        rollingTextView.setText("8")

        //swipeRefreshLayout
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            rollingTextView.setText("2")
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(mainActivity,"업데이트 완료",Toast.LENGTH_SHORT).show()
        }

        //30일 커밋 차트
        lineChart  = view.findViewById(R.id.line_chart)
        initLineChart()
        setDataToLineChart()
        val horizontalScrollView = view.findViewById<HorizontalScrollView>(R.id.horizontal_scroll)
        horizontalScrollView.post { horizontalScrollView.scrollTo(0,horizontalScrollView.height) }

        //팁 뷰페이저
        val textList = arrayListOf<String>("쉼에도 요령이 있는 법","아리가또","고자이마스","삼성전자 출신 팀쿡")
        val homePagerRecyclerAdapter= HomePagerRecyclerAdapter(textList)
        pagerRecyclerView = view.findViewById<ViewPager2>(R.id.main_view_pager)
        pagerRecyclerView.adapter = homePagerRecyclerAdapter
        pagerRecyclerView.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pagerRecyclerView.setCurrentItem(bannerPosition,false)
        val totalBannerNumView = view.findViewById<TextView>(R.id.total_banner_text_view)
        totalBannerNumView.text = numBanner.toString()
        val currentBannerNumView = view.findViewById<TextView>(R.id.current_banner_text_view)
        pagerRecyclerView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentBannerNumView.text = ((position%4)+1).toString()
                bannerPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                    ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                    // 뷰페이저 움직이는 중
                    ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    ViewPager2.SCROLL_STATE_SETTLING -> {}
                }
            }
        })

    }

    fun initLineChart(){
        lineChart.run {
            axisRight.isEnabled = false
            legend.isEnabled = false
            axisLeft.isEnabled  = false
            axisLeft.setDrawGridLines(false)
            description.isEnabled = false
            isDragXEnabled = true
            isScaleYEnabled = false
            isScaleXEnabled = false
            //add animation
            animateX(1000, Easing.EaseInSine)
            //range
            setScaleMinima(3f,1f)
            moveViewToX(xChartMax)
        }
        val xAxis: XAxis = lineChart.xAxis
        // to draw label on xAxis
        xAxis.run {
            setDrawGridLines(false)
            setDrawAxisLine(true)
            setDrawLabels(true)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = DefaultValueFormatter(0)
            textColor = resources.getColor(R.color.text_color,null)
            textSize = 10f
            granularity = 0f
            labelRotationAngle = 0f
        }
    }

    fun setDataToLineChart(){
        val entries: ArrayList<Entry> = ArrayList()

        val dateList = addXAisle()
        for (i in dateList.indices){
            addChartData(dateList[i],(1..10).random())
        }
        chartDatas.reverse()
        for (i in chartDatas.indices){
            var chartData = chartDatas[i]
            entries.add(Entry(chartData.day.toFloat(),chartData.commitNum.toFloat()))
        }
        Log.d("dd",chartDatas.toString())

        val lineDataSet = LineDataSet(entries,"")
        lineDataSet.run {
            color = resources.getColor(R.color.point_color,null)
            circleRadius = 5f
            lineWidth = 3f
            setCircleColor(resources.getColor(R.color.graph_dot_color,null))
            circleHoleColor = resources.getColor(R.color.graph_dot_color,null)
            setDrawHighlightIndicators(false)
            setDrawValues(true) // 숫자표시
            valueFormatter = DefaultValueFormatter(0)
            valueTextSize = 10f
        }
        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.invalidate()
    }

    fun addXAisle() : ArrayList<String>{

        val dateList = arrayListOf<String>()
        for (i in 0..29){
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE,-i)
            val todayDate = cal.time
            val dateFormat = SimpleDateFormat("dd", Locale.KOREA).format(todayDate)
            dateList.add(dateFormat)
        }
        return dateList
    }

    fun addChartData(day: String, commitNum : Int){
        val item = ChartData(day,commitNum)
        chartDatas.add(item)
    }

    fun autoScrollStart(intervalTime : Long){
        homeHandler.removeMessages(0) //핸들러 늘어남 방지
        homeHandler.sendEmptyMessageDelayed(0,intervalTime) // interval만큼 반복실행
    }

    fun autoScrollStop(){
        homeHandler.removeMessages(0)//핸들러 중지
    }

    fun ViewPager2.setCurrentItemWithDuration(
        item : Int, duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = pagerRecyclerView.width
    ){
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0

        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
            override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
            override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
        })

        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }

    inner class HomeHandler : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == 0){
                pagerRecyclerView.setCurrentItemWithDuration(++bannerPosition,500)
                autoScrollStart(intervalTime)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }

    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }
}