package com.example.withub.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainAdapter.HomePagerRecyclerAdapter
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.RollingTextView
import com.yy.mobile.rollingtextview.strategy.Strategy

class HomeFragment : Fragment(){

    lateinit var mainActivity: MainActivity

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

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) // 드로어레이아웃 swipe 잠금

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //팁 뷰페이저
        var textList = arrayListOf<String>("쉼에도 요령이 있는 법","아리가또","고자이마스","삼성전자 출신 팀쿡")
        var homePagerRecyclerAdapter= HomePagerRecyclerAdapter(textList)
        var pagerRecyclerView = view.findViewById<ViewPager2>(R.id.main_view_pager)
        var dotsIndicator = view.findViewById<WormDotsIndicator>(R.id.main_dot_indicator)
        pagerRecyclerView.adapter = homePagerRecyclerAdapter
        dotsIndicator.setViewPager2(pagerRecyclerView)
        pagerRecyclerView.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }
}