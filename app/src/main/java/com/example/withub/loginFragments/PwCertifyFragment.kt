package com.example.withub.loginFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.withub.FindPwActivity
import com.example.withub.LoginActivity
import com.example.withub.R

class PwCertifyFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.pwcertify_fragment, container, false)
        val findPwActivity = activity as FindPwActivity
        val findPwBackBtn = findPwActivity.findViewById<Button>(R.id.find_pw_back_btn)

        findPwBackBtn.setOnClickListener{
            val intent = Intent(findPwActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}