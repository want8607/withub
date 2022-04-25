package com.example.withub


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.withub.mainActivityAdapters.NavFriendRVAdapter
import com.example.withub.mainFragments.CommitFragement
import com.example.withub.mainFragments.HomeFragment
import com.example.withub.mainFragments.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView : NavigationView
    lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //첫 프래그먼트 설정
        if (savedInstanceState == null) {
            setFragment(HomeFragment(),"Home")
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
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.tap_home ->{
                    setFragment(HomeFragment(),"Home")
                }
                R.id.tap_ranking ->{
                    setFragment(RankingFragment(),"Ranking")
                }
                R.id.tap_commit ->{
                    setFragment(CommitFragement(),"Commit")
                }
            }
            true
        }
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
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment != null && fragment.isVisible) {
                    with(fragment.childFragmentManager) {
                        if (backStackEntryCount > 0) {
                            popBackStack()
                            return
                        }
                    }
                }
            }
            super.onBackPressed()
        }
    }

    fun setFragment(fragment: Fragment,tag: String){
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        //트랜잭션에 tag로 전달된 fragment가 없을 경우 add
        if(manager.findFragmentByTag(tag) == null){
            ft.add(R.id.main_frame_layout, fragment, tag)
        }

        //작업이 수월하도록 manager에 add되어있는 fragment들을 변수로 할당해둠
        val home = manager.findFragmentByTag("Home")
        val ranking = manager.findFragmentByTag("Ranking")
        val commit = manager.findFragmentByTag("Commit")

        //모든 프래그먼트 hide
        if(home!=null){
            ft.hide(home)
        }
        if(ranking!=null){
            ft.hide(ranking)
        }
        if(commit!=null){
            ft.hide(commit)
        }

        //선택한 항목에 따라 그에 맞는 프래그먼트만 show
        if(tag == "Home"){
            if(home!=null){
                ft.show(home)
            }
        }
        else if(tag == "Ranking"){
            if(ranking!=null){
                ft.show(ranking)
            }
        }
        else if(tag == "Commit"){
            if(commit!=null){
                ft.show(commit)
            }
        }
        //마무리
        ft.commitAllowingStateLoss()
        //ft.commit()
    }
}