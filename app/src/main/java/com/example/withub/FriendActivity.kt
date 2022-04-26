package com.example.withub

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.RollingTextView
import com.yy.mobile.rollingtextview.strategy.Strategy

class FriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val friendCommitTextView = findViewById<TextView>(R.id.friend_activity_friend_commit_text)
        friendCommitTextView.text = getString(R.string.friend_commit_average,friendNickName)

        val areaCommitTextView = findViewById<TextView>(R.id.friend_activity_area_commit_text)
        areaCommitTextView.text = getString(R.string.friend_commit_average,friendNickName)

        val vsFriendNameTextView = findViewById<TextView>(R.id.friend_activity_friend_name)
        vsFriendNameTextView.text = friendNickName

        val recentCommitTextView = findViewById<TextView>(R.id.friend_activity_recent_commit)
        recentCommitTextView.text = getString(R.string.friend_recent_commit,friendNickName)

        //롤링 텍스트 설정
        val rollingTextView = findViewById<RollingTextView>(R.id.friend_activity_rolling_commit_view)
        rollingTextView.animationDuration = 1000L
        rollingTextView.charStrategy = Strategy.NormalAnimation()
        rollingTextView.addCharOrder(CharOrder.Number)
        rollingTextView.animationInterpolator = AccelerateDecelerateInterpolator()
        rollingTextView.setText("8")
    }
}