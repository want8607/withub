package com.example.withub

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.withub.mainFragments.MyInfoFragment

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.account_activity)

        //비밀번호 변경
        findViewById<LinearLayout>(R.id.account_activity_change_password_view).setOnClickListener {
            val intent = Intent(applicationContext, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        //회원탈퇴
        findViewById<LinearLayout>(R.id.account_activity_delete_view).setOnClickListener {
            val intent = Intent(applicationContext, ResignAccount::class.java)
            startActivity(intent)
        }

    }
}