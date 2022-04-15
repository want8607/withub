package com.example.withub.activityAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.withub.BaseSwipeAdapter
import com.example.withub.R
import com.example.withub.SwipeAdapterInterface
import com.example.withub.SwipeItemManagerImpl
import kotlin.collections.ArrayList

class NavFriendRVAdapter(val context : Context, val items : ArrayList<String>) : RecyclerView.Adapter<NavFriendRVAdapter.Holder>(){
    lateinit var itemManager: SwipeItemManagerImpl
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavFriendRVAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_activity_nav_recycler_view_item, parent, false)
        var swipeLayout = view.findViewById<SwipeLayout>(R.id.swipe_view)
        swipeLayout.showMode = SwipeLayout.ShowMode.LayDown
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right,view.findViewById(R.id.swipe_bottom_wrapper))
        var mItemManager = BaseSwipeAdapter().mItemManager
        swipeLayout.addSwipeListener(object : SwipeLayout.SwipeListener{
            override fun onStartOpen(layout: SwipeLayout?) {
                mItemManager.closeAllExcept(layout!!)
                Log.d("dksl","dkjdk")
            }

            override fun onOpen(layout: SwipeLayout?) {
            }

            override fun onStartClose(layout: SwipeLayout?) {
            }

            override fun onClose(layout: SwipeLayout?) {
            }

            override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
            }

            override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
            }

        })
        return Holder(view)
    }

    override fun onBindViewHolder(holder: NavFriendRVAdapter.Holder, position: Int) {
        holder.bind(position)    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){

        val friendNameTextView = itemView?.findViewById<TextView>(R.id.nav_friend_name)
        fun bind(position: Int){
            friendNameTextView?.text = items[position]
        }

    }

}
