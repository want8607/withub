package com.example.withub.mainFragments

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.withub.*
import com.example.withub.mainFragments.mainFragmentAdapters.HomePagerRecyclerAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.RollingTextView
import com.yy.mobile.rollingtextview.strategy.Strategy
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(){

    lateinit var mainActivity: MainActivity
    lateinit var rollingTextView: RollingTextView //오늘 커밋 뷰
    lateinit var commitGrassImgView : ImageView //잔디
    lateinit var pagerRecyclerView: ViewPager2
    lateinit var lineChart: LineChart //라인 차트
    lateinit var friendAvgCommitView : RollingTextView // 친구 커밋 평균 뷰
    lateinit var areaAvgCommitView : RollingTextView // 지역 커밋 평균 뷰
    var myDataApi = RetrofitClient.initRetrofit().create(MyDataApi::class.java)
    var intervalTime = 4000.toLong()
    var bannerPosition = (Int.MAX_VALUE/2)+1
    var numBanner = 4
    var homeHandler = HomeHandler()

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
        rollingTextView = view.findViewById<RollingTextView>(R.id.rolling_commit)
        rollingTextView.animationDuration = 1000L
        rollingTextView.charStrategy = Strategy.NormalAnimation()
        rollingTextView.addCharOrder(CharOrder.Number)
        rollingTextView.animationInterpolator = AccelerateDecelerateInterpolator()

        //30일 커밋 차트
        lineChart  = view.findViewById(R.id.line_chart)
        val horizontalScrollView = view.findViewById<HorizontalScrollView>(R.id.horizontal_scroll)
        horizontalScrollView.post { horizontalScrollView.scrollTo(lineChart.width,0) }

        //친구 평균 뷰
        friendAvgCommitView = view.findViewById(R.id.home_fragment_my_friend_commit_avg)
        friendAvgCommitView.animationDuration = 1000L
        friendAvgCommitView.charStrategy = Strategy.NormalAnimation()
        friendAvgCommitView.addCharOrder(CharOrder.Number)
        friendAvgCommitView.animationInterpolator = AccelerateDecelerateInterpolator()

        //지역 평균 뷰
        areaAvgCommitView = view.findViewById(R.id.home_fragment_my_area_commit_avg)
        areaAvgCommitView.animationDuration = 1000L
        areaAvgCommitView.charStrategy = Strategy.NormalAnimation()
        areaAvgCommitView.addCharOrder(CharOrder.Number)
        areaAvgCommitView.animationInterpolator = AccelerateDecelerateInterpolator()

        //커밋 잔디
        commitGrassImgView = view.findViewById(R.id.main_commit_grass_img_view)

        //팁 뷰페이저
        //이미지 넣기
        val imgList = arrayListOf<Int>(R.drawable.view_pager1,R.drawable.view_pager2,R.drawable.view_pager3,R.drawable.view_pager4)
        //url 넣기
        val urlList = arrayListOf<String>(
            "https://www.youtube.com/watch?v=NOVDVW5dask",
            "https://www.youtube.com/watch?v=shZtNaSV5Tk",
            "https://www.youtube.com/watch?v=kp5CEADyTFs",
            "https://www.youtube.com/watch?v=Ru_bHWAqdSM")

        val homePagerRecyclerAdapter= HomePagerRecyclerAdapter(mainActivity,Glide.with(this),imgList,urlList)
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

        //swipeRefreshLayout
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {getMainData()}
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(mainActivity,"업데이트 완료",Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {getMainData()}
    }

    fun initLineChart(dateList : List<MyThirtyCommits>){
        lineChart.run {
            axisRight.isEnabled = false
            legend.isEnabled = false
            axisLeft.isEnabled  = false
            axisLeft.setDrawGridLines(false)
            description.isEnabled = false
            isDragXEnabled = true
            isScaleYEnabled = false
            isScaleXEnabled = false
        }
        val xAxis: XAxis = lineChart.xAxis
        // to draw label on xAxis
        xAxis.run {
            setDrawGridLines(false)
            setDrawAxisLine(true)
            setDrawLabels(true)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = XAxisCustomFormatter(addXAisle(dateList))
            textColor = resources.getColor(R.color.text_color,null)
            textSize = 10f
            labelRotationAngle = 0f
            setLabelCount(30,false)
        }
    }

    inner class XAxisCustomFormatter( val xAxisData : ArrayList<String>) : ValueFormatter(){

        override fun getFormattedValue(value: Float): String {
            return xAxisData[(value).toInt()]
        }
    }
    fun setDataToLineChart(commitList : List<MyThirtyCommits>){

        val entries: ArrayList<Entry> = ArrayList()
        for (i in commitList.indices){
            entries.add(Entry(i.toFloat(),commitList[i].commit.toFloat()))
        }
        val lineDataSet = LineDataSet(entries,"entries")
        lineDataSet.run {
            color = resources.getColor(R.color.point_color,null)
            circleRadius = 5f
            lineWidth = 3f
            setCircleColor(resources.getColor(R.color.graph_dot_color,null))
            circleHoleColor = resources.getColor(R.color.graph_dot_color,null)
            setDrawHighlightIndicators(false)
            setDrawValues(true) // 숫자표시
            valueTextColor = resources.getColor(R.color.text_color,null)
            valueFormatter = DefaultValueFormatter(0)
            valueTextSize = 10f
        }
        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }

    fun addXAisle(dateList : List<MyThirtyCommits>) : ArrayList<String>{
        val dataTextList = ArrayList<String>()
        for (i in dateList.indices){
            val textSize = dateList[i].date.length
            val dateText = dateList[i].date.substring(textSize-2,textSize)
            if(dateText=="01"){
                dataTextList.add(dateList[i].date)
            }else{
                dataTextList.add(dateText)
            }
        }

        return dataTextList
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


    suspend fun getMainData(){
        var handler = CoroutineExceptionHandler{_,exception->
            Log.d("error",exception.toString())
        }
        CoroutineScope(Dispatchers.Main).launch(handler){
            //호출
            val callMainDataApi : Deferred<MyData> = async(Dispatchers.IO){
                myDataApi.getMyData(MyApp.prefs.accountToken!!)
            }
            //변경
            Log.d("success",callMainDataApi.await().success.toString())
            Log.d("message",callMainDataApi.await().message)
            Log.d("daily",callMainDataApi.await().daily_commit.toString())
            Log.d("area",callMainDataApi.await().area_avg.toString())
            Log.d("friend",callMainDataApi.await().friend_avg.toString())
            Log.d("thirty",callMainDataApi.await().thirty_commit.toString())
            Log.d("commiter",callMainDataApi.await().committer)
            rollingTextView.setText(callMainDataApi.await().daily_commit.toString())//오늘 커밋
            if (callMainDataApi.await().friend_avg == -1f){
                friendAvgCommitView.setText("친구추가하세용")
            }else{
                friendAvgCommitView.setText(callMainDataApi.await().friend_avg.toString())
            }
            //친구 커밋
            areaAvgCommitView.setText(callMainDataApi.await().area_avg.toString())//지역 커밋
            initLineChart(callMainDataApi.await().thirty_commit)//차트 x축
            setDataToLineChart(callMainDataApi.await().thirty_commit)//차트 커밋수 조절
            getGrassImg(callMainDataApi.await().committer) //잔디 불러오기
        }
    }
    
    fun getGrassImg(url : String){
        GlideToVectorYou.init()
            .with(mainActivity)
            .withListener(object : GlideToVectorYouListener{
                override fun onLoadFailed() {
                }
                override fun onResourceReady() {
                }
            })
            .load("https://ghchart.rshah.org/219138/$url".toUri(),commitGrassImgView)
    }
}