package com.example.withub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.withub.loginFragments.ChangePwFragment
import com.example.withub.loginFragments.EmailCertifyFragment
import com.example.withub.loginFragments.PwCertifyFragment
import com.example.withub.loginFragments.TermsOfUseFragment

class FindPwActivity: AppCompatActivity() {
    lateinit var token:String
    val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findpw_activity)
        if (savedInstanceState == null) {
            fragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragmentArea_find_pw, PwCertifyFragment(), "termsOfUseFragment")
            }
        }
    }

    fun tokenInform(tokenValue:String){
        token = tokenValue
        var fragment = ChangePwFragment()
        var myBundle = Bundle()
        myBundle.putString("token", token)
        fragment.arguments = myBundle
        fragmentManager.commit {
            add(R.id.fragmentArea_find_pw, fragment, "changePwFragment")
            addToBackStack(null)
        }
    }
}