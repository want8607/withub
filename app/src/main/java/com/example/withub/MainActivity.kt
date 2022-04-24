package com.example.withub


import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.withub.mainActivityAdapters.NavFriendRVAdapter
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
        //첫 프래그먼트 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,HomeFragment()).commit()
        }

        drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.navigation_view)

        // 네비게이션 드로어 설정
        var navHeader = findViewById<View>(R.id.main_nav_header)
        var navHeaderImageView = navHeader.findViewById<ImageView>(R.id.nav_header_img)

        Glide.with(this)
            .load("https://avatars.githubusercontent.com/u/84075111?v=4")
            .placeholder(R.mipmap.nav_header_loading_img) // 로딩 이미지
            .error(R.mipmap.nav_header_loading_img)//로딩 실패 이미지
            .circleCrop()//원형으로 깎기
            .into(navHeaderImageView)

        //네비게이션 드로어 친구목록 리사이클러뷰 설정
        val decoration = DividerItemDecoration(applicationContext, VERTICAL)
        val items = arrayListOf<String>("ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ")
        val recyclerView = navHeader.findViewById<RecyclerView>(R.id.nav_friend_recycler_View)
        recyclerView.addItemDecoration(decoration)
        val navFriendRVAdapter  = NavFriendRVAdapter(this, items )
        recyclerView.adapter = navFriendRVAdapter

        //네비게이션 드로어 친구추가 AlterDialog
        navHeader.findViewById<ImageButton>(R.id.nav_add_friend_button).setOnClickListener {
            val input = EditText(this)
            input.hint = "닉네임"
            input.setSingleLine()
            val inputContainer = LinearLayout(this)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.leftMargin = resources.getDimensionPixelSize(R.dimen.dialog_margin)
            params.rightMargin = resources.getDimensionPixelSize(R.dimen.dialog_margin)
            input.layoutParams = params
            inputContainer.addView(input)
            val dialog : AlertDialog.Builder = AlertDialog.Builder(this)
            dialog.setTitle("친구추가")
                .setMessage("친구의 닉네임을 입력해 주세요.")
                .setView(inputContainer)
                .setPositiveButton("추가"){ dialogInterface, i -> navFriendRVAdapter.addItem(input.text.toString()) }
                .setNegativeButton("취소"){ dialogInterface, i ->  }
                .show()
        }


        //네비게이션 드로어 닫기
        navHeader.findViewById<ImageButton>(R.id.nav_exit_button).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navFriendRVAdapter.closeSwipeView()
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

    //홈에서 네비게이션 드로어 열기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 뒤로가기 눌렀을 때 네비게이션 드로어 닫기
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

    fun addFriendToList(){

    }

}