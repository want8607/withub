package com.example.withub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.withub.mainFragments.mainFragmentAdapters.EmailCertifyFragment

class SignupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, EmailCertifyFragment()).commit()

        }
    }
}