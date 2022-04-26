package com.example.withub.mainFragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainFragmentAdapters.SignupActivity

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WITHUB)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        var signupBtn = findViewById<TextView>(R.id.signup_btn)

        signupBtn.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}