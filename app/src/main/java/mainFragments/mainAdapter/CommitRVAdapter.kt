package com.example.withub.mainFragments.mainAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.R

class CommitRVAdapter(val context : Context, var list : ArrayList<CommitData>) : RecyclerView.Adapter<CommitRVAdapter.Holder>(){

    inner class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){

        var dateTextview = itemView?.findViewById<TextView>(R.id.commit_date_text_view)
        var repositoryName = itemView?.findViewById<TextView>(R.id.commit_repository_name)
        var commitMessage = itemView?.findViewById<TextView>(R.id.commit_message_text_view)

        fun bind(position: Int){
            dateTextview?.text = list[position].date
            repositoryName?.text = list[position].repository
            commitMessage?.text = list[position].commitMessage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitRVAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.commit_recycler_view_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}