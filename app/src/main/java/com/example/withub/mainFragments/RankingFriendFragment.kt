package com.example.withub.mainFragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.withub.*
import com.example.withub.mainFragments.mainFragmentAdapters.ExpandableRVAdapter
import com.example.withub.mainFragments.mainFragmentAdapters.FriendRankingData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RankingFriendFragment : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var recyclerView : RecyclerView
    lateinit var expandableAdapter : ExpandableRVAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var commitApi = RetrofitClient.initRetrofit().create(CommitApi::class.java)
    val rankingDataList : ArrayList<List<FriendRankingData>> = arrayListOf(
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
        recyclerView = view.findViewById<RecyclerView>(R.id.ranking_friend_recycler_view)
        recyclerView.adapter = expandableAdapter
        recyclerView.setHasFixedSize(true)

        //리프레시 구현
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.ranking_friend_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {getFriendRankingData()}
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(mainActivity,"업데이트 완료", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun getFriendRankingData(){

        val handler = CoroutineExceptionHandler{_,exception->
            Log.d("error",exception.toString())
            Log.d("error",exception.cause.toString())
        }

        CoroutineScope(Dispatchers.Main).launch(handler){

            Log.d("success",commitApi.getFriendRank(MyApp.prefs.accountToken!!).message)
            Log.d("success",commitApi.getFriendRank(MyApp.prefs.accountToken!!).success.toString())
            Log.d("daily",commitApi.getFriendRank(MyApp.prefs.accountToken!!).daily_rank.toString())
            Log.d("weekly_rank",commitApi.getFriendRank(MyApp.prefs.accountToken!!).weekly_rank.toString())
            Log.d("monthly_rank",commitApi.getFriendRank(MyApp.prefs.accountToken!!).monthly_rank.toString())
            Log.d("continuous_rank",commitApi.getFriendRank(MyApp.prefs.accountToken!!).continuous_rank.toString())
//            commitApi.getFriendRank(MyApp.prefs.accountToken!!).daily_rank
//            commitApi.getFriendRank(MyApp.prefs.accountToken!!).weekly_rank
//            commitApi.getFriendRank(MyApp.prefs.accountToken!!).monthly_rank
//            commitApi.getFriendRank(MyApp.prefs.accountToken!!).continuous_rank
        }
    }
}