package com.example.withub.com.example.withub


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.withub.R
import com.example.withub.activityAdapters.NavFriendRVAdapter
import com.example.withub.mainFragments.CommitFragement
import com.example.withub.mainFragments.HomeFragment
import com.example.withub.mainFragments.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.navigation_view)

        // 바 네비게이션 설정
        var navHeader = findViewById<View>(R.id.main_nav_header)
        var navHeaderImageView = navHeader.findViewById<ImageView>(R.id.nav_header_img)

        Glide.with(this)
            .load("https://avatars.githubusercontent.com/u/84075111?v=4")
            .placeholder(R.mipmap.nav_header_loading_img) // 로딩 이미지
            .error(R.mipmap.nav_header_loading_img)//로딩 실패 이미지
            .circleCrop()//원형으로 깎기
            .into(navHeaderImageView)

        //바 네비게이션 친구목록 리사이클러뷰 설정
        val decoration = DividerItemDecoration(applicationContext, VERTICAL)
        val items = arrayListOf<String>("ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ")
        val recyclerView = navHeader.findViewById<RecyclerView>(R.id.nav_friend_recycler_View)
        recyclerView.addItemDecoration(decoration)
        val navFriendRVAdapter  = NavFriendRVAdapter(this, items )
        recyclerView.adapter = navFriendRVAdapter

        //첫 프래그먼트 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,HomeFragment()).commit()
        }

        //바텀 네비게이션 뷰
        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.tap_home ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,HomeFragment()).commit()
                }
                R.id.tap_ranking ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,RankingFragment()).commit()
                }
                R.id.tap_commit ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,CommitFragement()).commit()
                }
            }
            true
        }
        bottomNavigationView.menu.findItem(R.id.tap_home).isChecked = true

    }

    //홈에서 바 네비게이션 열기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 뒤로가기 눌렀을 때 네비게이션 닫기
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}