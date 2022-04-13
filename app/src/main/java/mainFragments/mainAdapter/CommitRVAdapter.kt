package com.example.withub.mainFragments.mainAdapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.MainActivity
import com.example.withub.R

class CommitRVAdapter(val context : Context, var list : ArrayList<CommitData>) : RecyclerView.Adapter<CommitRVAdapter.Holder>(){

    inner class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){

        var dateTextview = itemView?.findViewById<TextView>(R.id.commit_date_text_view)
        var repositoryName = itemView?.findViewById<TextView>(R.id.commit_repository_name)
        var commitMessage = itemView?.findViewById<TextView>(R.id.commit_message_text_view)
        var commitClickArea = itemView?.findViewById<ConstraintLayout>(R.id.commit_constraint_layout)
        fun bind(position: Int){
            dateTextview?.text = list[position].date
            repositoryName?.text = list[position].repository
            commitMessage?.text = list[position].commitMessage
            commitClickArea?.setOnClickListener {
                var intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/want8607/stageus_withub_2/commit/00b8a6d4020e02844b528c2f29b250a507c30739"))
                context.startActivity(intent)
            }
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