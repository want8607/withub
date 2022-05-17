package com.example.withub.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.withub.*
import com.example.withub.mainFragments.mainFragmentAdapters.CommitData
import com.example.withub.mainFragments.mainFragmentAdapters.CommitRVAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class CommitFragment : Fragment() {

    var commitApi = RetrofitClient.initRetrofit().create(CommitApi::class.java)
    lateinit var mainActivity : MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.commit_fragment,container,false)
        mainActivity = activity as MainActivity
        val list : ArrayList<CommitData> = arrayListOf(
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다."),
            CommitData("2022-03-25 19:49","want8607/ediya_kiosk","UPADATE\n -다했다.")
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.commit_recycler_view)
        val adapter  = CommitRVAdapter(mainActivity,list)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //리프레시 구현
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.commit_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch { getMyCommitData() }
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(mainActivity,"업데이트 완료", Toast.LENGTH_SHORT).show()
        }
    }

    fun getMyCommitData(){
        CoroutineScope(Dispatchers.IO).launch{
            Log.d("message",commitApi.getMyCommitList(MyApp.prefs.accountToken!!).message)
            Log.d("success",commitApi.getMyCommitList(MyApp.prefs.accountToken!!).success.toString())
            Log.d("commit",commitApi.getMyCommitList(MyApp.prefs.accountToken!!).commits.toString())
        }
    }
}