package com.example.withub.mainFragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainAdapter.ExpandableAdapter


class RankingFriendFragment : Fragment() {
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.ranking_friend_fragment,container,false)
        mainActivity = activity as MainActivity
        val expandableAdapter = ExpandableAdapter(mainActivity)
        Log.d("mdksadma","dddd")
        val recyclerView = view.findViewById<RecyclerView>(R.id.ranking_friend_recycler_view)
        recyclerView.adapter = expandableAdapter
        recyclerView.setHasFixedSize(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}