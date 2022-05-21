package com.example.withub

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.withub.mainActivityAdapters.FriendRepoRVAdapter
import com.example.withub.mainFragments.mainFragmentAdapters.ExpandableRVAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
import com.robinhood.ticker.TickerView
import kotlinx.coroutines.*

class FriendActivity : AppCompatActivity() {

    lateinit var lineChart: LineChart

    var friendApi = RetrofitClient.initRetrofit().create(FriendApi::class.java)
    val handler = CoroutineExceptionHandler{_,exception->
        Log.d("error",exception.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.friend_activity)
        //친구 이름 가져오기
        val friendNickName = intent.getStringExtra("friendNickName")!!
        val myNickName = intent.getStringExtra("myNickName")!!
        val myAvatar = intent.getStringExtra("myAvatar")!!
        val friendAvatar = intent.getStringExtra("friendAvatar")!!
        //뒤로가기
        val backBtn = findViewById<ImageButton>(R.id.friend_activity_back_btn)
        backBtn.setOnClickListener {
            finish()
        }

        //닉네임 설정
        val toolbarNickNameView = findViewById<TextView>(R.id.friend_activity_toolbar_textview)
        toolbarNickNameView.text = getString(R.string.neam,friendNickName)

        //롤링 텍스트 설정
        val friendTodayCommitView = findViewById<TickerView>(R.id.friend_activity_friend_today_commit)
        
        //커밋 승자 뷰
        val myCrown = findViewById<ImageView>(R.id.friend_activity_left_crown)
        val friendCrown = findViewById<ImageView>(R.id.friend_activity_right_crown)

        val myImageView = findViewById<ImageView>(R.id.friend_activity_user_Image)
        val friendImageView = findViewById<ImageView>(R.id.friend_activity_friend_Image)
        glide(myAvatar,myImageView)
        glide(friendAvatar,friendImageView)
        //닉네임설정
        val myName = findViewById<TextView>(R.id.friend_activity_user_name)
        val friendName = findViewById<TextView>(R.id.friend_activity_friend_name)
        friendName.text = friendNickName
        myName.text = myNickName
        //횟수
        val myMonthCommitNumView = findViewById<TextView>(R.id.friend_activity_user_month_commit_num)
        val friendMontCommitNumView = findViewById<TextView>(R.id.friend_activity_friend_month_commit_num)
        //커밋 잔디
        val commitGrassImgView = findViewById<ImageView>(R.id.friend_commit_grass_img_view)
        val commitVsText = findViewById<TextView>(R.id.friend_activity_vs)
        val repositoryTitle = findViewById<TextView>(R.id.friend_activity_repository_title)
        repositoryTitle.text = getString(R.string.friend_repository_list,friendNickName)
        //그래프 설정
        lineChart  = findViewById(R.id.friend_line_chart)
        val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.friend_horizontal_scroll)
        horizontalScrollView.post { horizontalScrollView.scrollTo(lineChart.width,0) }

        CoroutineScope(Dispatchers.Main).launch(handler) {
            val callFriendDataApi = withContext(Dispatchers.IO) {
                friendApi.getFriendInfo(MyApp.prefs.accountToken!!,friendNickName)
            }

            friendTodayCommitView.text = callFriendDataApi.friend_daily.toString()//오늘 커밋

            //친구 커밋
            initLineChart(callFriendDataApi.thirty_commit)//차트 x축
            setDataToLineChart(callFriendDataApi.thirty_commit)//차트 커밋수 조절
            getGrassImg(commitGrassImgView,callFriendDataApi.committer) //잔디 불러오기

            //승자
            myMonthCommitNumView.text = callFriendDataApi.my_month_total.toString()+"회"
            friendMontCommitNumView.text = callFriendDataApi.friend_month_total.toString()+"회"

            if(callFriendDataApi.friend_month_total>callFriendDataApi.my_month_total){
                //친구승
                myCrown.visibility = View.INVISIBLE
                friendCrown.visibility = View.VISIBLE
                commitVsText.text = "<"

            }else if(callFriendDataApi.friend_month_total<callFriendDataApi.my_month_total){
                //나 승
                myCrown.visibility = View.VISIBLE
                friendCrown.visibility = View.INVISIBLE
                commitVsText.text = ">"
            }else if(callFriendDataApi.friend_month_total==callFriendDataApi.my_month_total){
                //같을 때
                myCrown.visibility = View.GONE
                friendCrown.visibility = View.GONE
                commitVsText.text = "="
            }

            //레포 설정
            val repoRVAdapter = FriendRepoRVAdapter(this@FriendActivity,callFriendDataApi.repository)
            val recyclerView = findViewById<RecyclerView>(R.id.friend_activity_recycler_view)
            recyclerView.adapter = repoRVAdapter
            recyclerView.setHasFixedSize(true)
        }

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.friend_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            CoroutineScope(Dispatchers.Main).launch(handler) {
                val callFriendDataApi = withContext(Dispatchers.IO) { friendApi.getFriendInfo(MyApp.prefs.accountToken!!,friendNickName) }
                friendTodayCommitView.text = callFriendDataApi.friend_daily.toString()//오늘 커밋

                //친구 커밋
                initLineChart(callFriendDataApi.thirty_commit)//차트 x축
                setDataToLineChart(callFriendDataApi.thirty_commit)//차트 커밋수 조절
                getGrassImg(commitGrassImgView,callFriendDataApi.committer) //잔디 불러오기

                //승자
                myMonthCommitNumView.text = callFriendDataApi.my_month_total.toString()
                friendMontCommitNumView.text = callFriendDataApi.friend_month_total.toString()

                if(callFriendDataApi.friend_month_total>callFriendDataApi.my_month_total){
                    //친구승
                    myCrown.visibility = View.INVISIBLE
                    friendCrown.visibility = View.VISIBLE

                }else if(callFriendDataApi.friend_month_total<callFriendDataApi.my_month_total){
                    //나 승
                    myCrown.visibility = View.VISIBLE
                    friendCrown.visibility = View.INVISIBLE
                }else if(callFriendDataApi.friend_month_total==callFriendDataApi.my_month_total){
                    //같을 때
                    myCrown.visibility = View.GONE
                    friendCrown.visibility = View.GONE
                }

                //레포 설정
                val repoRVAdapter = FriendRepoRVAdapter(this@FriendActivity,callFriendDataApi.repository)
                val recyclerView = findViewById<RecyclerView>(R.id.friend_activity_recycler_view)
                val decoration = DividerItemDecoration(applicationContext, RecyclerView.VERTICAL)
                recyclerView.adapter = repoRVAdapter
                recyclerView.setHasFixedSize(true)
                recyclerView.addItemDecoration(decoration)
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@FriendActivity,"업데이트 완료", Toast.LENGTH_SHORT).show()
            }
        }
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


    fun getGrassImg(imageView: ImageView,url : String){
        GlideToVectorYou.init()
            .with(this)
            .withListener(object : GlideToVectorYouListener{
                override fun onLoadFailed() {
                }
                override fun onResourceReady() {
                }
            })
            .load("https://ghchart.rshah.org/219138/$url".toUri(),imageView)
    }

    fun glide(url: String,imageView: ImageView){
        Glide.with(this@FriendActivity)
            .load(url.toUri())
            .error(R.mipmap.ic_launcher)
            .fallback(R.mipmap.ic_launcher)
            .circleCrop()
            .into(imageView)
    }
}