package com.example.withub.mainFragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainAdapter.ExpandableRVAdapter
import com.example.withub.mainFragments.mainAdapter.FriendRankingData


class RankingFriendFragment : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var recyclerView : RecyclerView
    lateinit var expandableAdapter : ExpandableRVAdapter

    val rankingDataList : ArrayList<ArrayList<FriendRankingData>> = arrayListOf(
        arrayListOf(
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3)
        ),
        arrayListOf(
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3)
        ),
        arrayListOf(
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3)
        ),
        arrayListOf(
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3),
            FriendRankingData("문승재",3)
        )
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.ranking_friend_fragment,container,false)
        mainActivity = activity as MainActivity

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandableAdapter = ExpandableRVAdapter(rankingDataList)
        Log.d("mdksadma","dddd")
        recyclerView = view.findViewById<RecyclerView>(R.id.ranking_recycler_view)
        recyclerView.adapter = expandableAdapter
        recyclerView.setHasFixedSize(true)
    }
}