package com.example.withub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.withub.loginFragments.ChangePwFragment
import com.example.withub.loginFragments.EmailCertifyFragment
import com.example.withub.loginFragments.PwCertifyFragment

class FindPwActivity: AppCompatActivity() {
    lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findpw_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea_find_pw, PwCertifyFragment()).commit()
        }

    }

    fun tokenInform(tokenValue:String){
        token = tokenValue
        var fragment = ChangePwFragment()
        var myBundle = Bundle()
        myBundle.putString("token", token)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }
}