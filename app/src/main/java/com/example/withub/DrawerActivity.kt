package com.example.withub

import android.content.Intent
import android.icu.number.Scale.none
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.withub.mainActivityAdapters.NavFriendRVAdapter

class DrawerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.drawer_activity)

        //헤더 이미지 설정
        var headerImgView = findViewById<ImageView>(R.id.drawer_header_img)
        Glide.with(this)
            .load("https://avatars.githubusercontent.com/u/84075111?v=4")
            .placeholder(R.mipmap.nav_header_loading_img) // 로딩 이미지
            .error(R.mipmap.nav_header_loading_img)//로딩 실패 이미지
            .circleCrop()//원형으로 깎기
            .into(headerImgView)
        
        //드로어 친구목록 리사이클러뷰 설정
        val decoration = DividerItemDecoration(applicationContext, RecyclerView.VERTICAL)
        val items = arrayListOf<String>("ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ","ㅎㅎ")
        val recyclerView = findViewById<RecyclerView>(R.id.drawer_friend_recycler_View)
        recyclerView.addItemDecoration(decoration)
        val navFriendRVAdapter  = NavFriendRVAdapter(this, items )
        recyclerView.adapter = navFriendRVAdapter

        //네비게이션 드로어 친구추가 AlterDialog
        findViewById<ImageButton>(R.id.drawer_add_friend_button).setOnClickListener {
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
                .setPositiveButton("추가"){ _, _ -> navFriendRVAdapter.addItem(input.text.toString()) }
                .setNegativeButton("취소"){ _, _ ->  }
                .show()
        }

        findViewById<ImageButton>(R.id.drawer_option_button).setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }

        //닫기
        findViewById<ImageButton>(R.id.drawer_exit_button).setOnClickListener {
            finish()
            navFriendRVAdapter.closeSwipeView()
        }


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.hold,R.anim.rightroleft_animation)
    }
}