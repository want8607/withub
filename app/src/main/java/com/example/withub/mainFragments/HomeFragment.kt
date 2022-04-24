package com.example.withub.mainFragments

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainFragmentAdapters.HomePagerRecyclerAdapter
import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.RollingTextView
import com.yy.mobile.rollingtextview.strategy.Strategy

class HomeFragment : Fragment(){

    lateinit var mainActivity: MainActivity
    lateinit var pagerRecyclerView: ViewPager2
    var intervalTime = 3000.toLong()
    var bannerPosition = (Int.MAX_VALUE/2)+1
    var numBanner = 4
    var homeHandler = HomeHandler()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.home_fragment,container,false)
        mainActivity = activity as MainActivity

        //toolbar 설정
        var toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar!!.setDisplayShowCustomEnabled(true)
        mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mainActivity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_nav)
        mainActivity.supportActionBar!!.setDisplayShowTitleEnabled(false) // 기본 타이틀 미사용

        var rollingTextView = view.findViewById<RollingTextView>(R.id.rolling_commit)
        rollingTextView.animationDuration = 1000L
        rollingTextView.charStrategy = Strategy.NormalAnimation()
        rollingTextView.addCharOrder(CharOrder.Number)
        rollingTextView.animationInterpolator = AccelerateDecelerateInterpolator()
        rollingTextView.setText("8")

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) // 드로어레이아웃 swipe 잠금

        //swipeRefreshLayout
        var swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            rollingTextView.setText("2")
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(mainActivity,"업데이트 완료",Toast.LENGTH_SHORT).show()
        }

        //팁 뷰페이저
        var textList = arrayListOf<String>("쉼에도 요령이 있는 법","아리가또","고자이마스","삼성전자 출신 팀쿡")
        var homePagerRecyclerAdapter= HomePagerRecyclerAdapter(textList)
        pagerRecyclerView = view.findViewById<ViewPager2>(R.id.main_view_pager)
        pagerRecyclerView.adapter = homePagerRecyclerAdapter
        pagerRecyclerView.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pagerRecyclerView.setCurrentItem(bannerPosition,false)
        var totalBannerNumView = view.findViewById<TextView>(R.id.total_banner_text_view)
        totalBannerNumView.text = numBanner.toString()
        var currentBannerNumView = view.findViewById<TextView>(R.id.current_banner_text_view)
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
                }
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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