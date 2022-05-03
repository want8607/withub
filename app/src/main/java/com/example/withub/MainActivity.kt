package com.example.withub


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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.withub.mainActivityAdapters.NavFriendRVAdapter
import com.example.withub.mainFragments.CommitFragment
import com.example.withub.mainFragments.HomeFragment
import com.example.withub.mainFragments.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //첫 프래그먼트 설정
        if (savedInstanceState == null) {
            setFragment(HomeFragment(),"Home")
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
                    setFragment(CommitFragment(),"Commit")
                }
            }
            true
        }
    }

    // 뒤로가기 눌렀을 때 네비게이션 드로어 닫기
    override fun onBackPressed() {
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment != null && fragment.isVisible) {
                    with(fragment.childFragmentManager) {
                        if (backStackEntryCount > 0) {
                            popBackStack()
                            return
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