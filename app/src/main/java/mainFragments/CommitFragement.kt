package com.example.withub.mainFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.MainActivity
import com.example.withub.R
import com.example.withub.mainFragments.mainAdapter.CommitData
import com.example.withub.mainFragments.mainAdapter.CommitRVAdapter

class CommitFragement : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}