package com.example.withub

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FriendActivity : AppCompatActivity() {

    lateinit var lineChart: LineChart
    var xAxisData = addXAisle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.friend_activity)

        //뒤로가기
        val backBtn = findViewById<ImageButton>(R.id.friend_activity_back_btn)
        backBtn.setOnClickListener {
            finish()
        }
        val friendNickName : String? = intent.getStringExtra("friendNickName")

        //닉네임 설정
        val toolbarNickNameView = findViewById<TextView>(R.id.friend_activity_toolbar_textview)
        toolbarNickNameView.text = getString(R.string.neam,friendNickName)


        val vsFriendNameTextView = findViewById<TextView>(R.id.friend_activity_friend_name)
        vsFriendNameTextView.text = friendNickName

        //롤링 텍스트 설정
        val rollingTextView = findViewById<RollingTextView>(R.id.friend_activity_rolling_commit_view)
        rollingTextView.animationDuration = 1000L
        rollingTextView.charStrategy = Strategy.NormalAnimation()
        rollingTextView.addCharOrder(CharOrder.Number)
        rollingTextView.animationInterpolator = AccelerateDecelerateInterpolator()
        rollingTextView.setText("8")

        //커밋 잔디
        val commitGrassImgView = findViewById<ImageView>(R.id.friend_commit_grass_img_view)
        GlideToVectorYou.init()
            .with(this)
            .withListener(object : GlideToVectorYouListener {
                override fun onLoadFailed() {
                }
                override fun onResourceReady() {
                }
            })
            .load("https://ghchart.rshah.org/219138/want8607".toUri(),commitGrassImgView)

        //그래프 설정
        lineChart  = findViewById(R.id.friend_line_chart)
        initLineChart()
        setDataToLineChart()
        val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.friend_horizontal_scroll)
        horizontalScrollView.post { horizontalScrollView.scrollTo(lineChart.width,0) }
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
        }
        val xAxis: XAxis = lineChart.xAxis
        // to draw label on xAxis
        xAxis.run {
            setDrawGridLines(false)
            setDrawAxisLine(true)
            setDrawLabels(true)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = XAxisCustomFormatter()
            textColor = resources.getColor(R.color.text_color,null)
            textSize = 10f
            labelRotationAngle = 0f
            setLabelCount(30,false)
        }
    }

    inner class XAxisCustomFormatter() : ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return xAxisData[(value).toInt()]
        }
    }

    fun setDataToLineChart(){
        val entries: ArrayList<Entry> = ArrayList()
        val ylist = mutableListOf<Int>(1,2,3,4,1,1,2,0,20,0,0,1,2,4,5,1,6,8,1,3,5,4,1,2,3,4,5,6,1,2)
        for (i in xAxisData.indices){
            entries.add(Entry(i.toFloat(),ylist[i].toFloat()))
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

    fun addXAisle() : ArrayList<String>{
        val dateList = arrayListOf<String>()
        for (i in 0..29){
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE,-i)
            val todayDate = cal.time
            val dateFormat = SimpleDateFormat("dd", Locale.KOREA).format(todayDate)
            if (dateFormat=="01"){
                dateList.add(SimpleDateFormat("MM-dd", Locale.KOREA).format(todayDate))
            }else{
                dateList.add(dateFormat)
            }
        }
        dateList.reverse()
        return dateList
    }
}