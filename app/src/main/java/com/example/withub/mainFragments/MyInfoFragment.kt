package com.example.withub.mainFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.withub.AccountActivity
import com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.SettingActivity

class MyInfoFragment: Fragment() {
    lateinit var mainActivity : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.myinfo_fragment, container, false)
        mainActivity = activity as MainActivity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changeGitHubInfoView = view.findViewById<ConstraintLayout>(R.id.my_info_change_github_info)
        val changeAccountView = view.findViewById<ConstraintLayout>(R.id.my_info_account)
        //깃허브 정보 변경
        changeGitHubInfoView.setOnClickListener {
//            val intent = Intent(mainActivity, SettingActivity::class.java)
//            startActivity(intent)
        }

        //계정 정보 변경
        changeAccountView.setOnClickListener {
            val intent = Intent(mainActivity, AccountActivity::class.java)
            startActivity(intent)
        }
    }
}