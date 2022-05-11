package com.example.withub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.withub.loginFragments.PwCertifyFragment

class FindPwActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findpw_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea_find_pw, PwCertifyFragment()).commit()
        }

    }
}