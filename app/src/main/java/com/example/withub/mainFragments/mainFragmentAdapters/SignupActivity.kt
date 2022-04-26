package com.example.withub.mainFragments.mainFragmentAdapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.withub.R

class SignupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

//        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea,LoginFragment()).commit()
    }
}