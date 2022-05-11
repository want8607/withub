package com.example.withub.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.withub.MainActivity
import com.example.withub.R

class MyInfoFragment: Fragment() {
    lateinit var mainActivity : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.myinfo_fragment,container,false)
        mainActivity = activity as MainActivity

        return view
    }
}