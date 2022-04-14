package com.example.withub.activityAdapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.R
import java.util.*
import kotlin.collections.ArrayList

class NavFriendRVAdapter(val context : Context, val items : ArrayList<String>) : RecyclerView.Adapter<NavFriendRVAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavFriendRVAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_activity_nav_recycler_view_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: NavFriendRVAdapter.Holder, position: Int) {
        holder.bind(position)    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeData(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){

        val friendNameTextView = itemView?.findViewById<TextView>(R.id.nav_friend_name)
        val removeView = itemView?.findViewById<TextView>(R.id.nav_remove_text_view)
        fun bind(position: Int){
            friendNameTextView?.text = items[position]
            removeView?.setOnClickListener {
                removeData(this.layoutPosition)
            }
        }
    }

}