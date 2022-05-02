package com.example.withub.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainFragmentAdapters.CommitData
import com.example.withub.mainFragments.mainFragmentAdapters.CommitRVAdapter

class CommitFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.commit_fragment,container,false)
        val mainActivity = activity as MainActivity
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

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) // 드로어레이아웃 swipe 잠금

        //리프레시 구현
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.commit_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(mainActivity,"업데이트 완료", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}