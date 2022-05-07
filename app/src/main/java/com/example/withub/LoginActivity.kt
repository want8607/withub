package com.example.withub

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WITHUB)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val signupBtn = findViewById<TextView>(R.id.signup_btn)
        val idFindingBtn = findViewById<TextView>(R.id.id_finding_btn)
        val pwFindingBtn = findViewById<TextView>(R.id.pw_finding_btn)

        signupBtn.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        idFindingBtn.setOnClickListener{
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
        }

        pwFindingBtn.setOnClickListener{
            val intent = Intent(this, FindPwActivity::class.java)
            startActivity(intent)
        }
    }
}