package com.example.withub.mainActivityAdapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.daimajia.swipe.implments.SwipeItemMangerImpl
import com.example.withub.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class NavFriendRVAdapter(val context : Context, val items : ArrayList<String>) : RecyclerSwipeAdapter<NavFriendRVAdapter.Holder>(){

    val friendApi = RetrofitClient.initRetrofit().create(FriendApi::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavFriendRVAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_activity_nav_recycler_view_item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: NavFriendRVAdapter.Holder, position: Int) {

        mItemManger.bindView(holder.itemView,position)
        holder.swipeView?.addSwipeListener(object : SwipeLayout.SwipeListener{
            override fun onStartOpen(layout: SwipeLayout?) {
                mItemManger.closeAllExcept(layout)
            }

            override fun onOpen(layout: SwipeLayout?) {}

            override fun onStartClose(layout: SwipeLayout?) {}

            override fun onClose(layout: SwipeLayout?) {}

            override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {}

            override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {}

        })
        holder.bind(position)    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe_view
    }

    inner class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){

        val deleteBtn = itemView?.findViewById<TextView>(R.id.swipe_delete_button)
        val friendNameTextView = itemView?.findViewById<TextView>(R.id.nav_friend_name)
        val swipeView = itemView?.findViewById<SwipeLayout>(R.id.swipe_view)
        val friendItem = itemView?.findViewById<ConstraintLayout>(R.id.nav_friend_recycler_item)

        fun bind(position: Int){
            friendNameTextView?.text = items[position]

            friendItem?.setOnClickListener {
                val intent = Intent(context,FriendActivity::class.java)
                intent.putExtra("friendNickName",items[position])
                context.startActivity(intent)
            }

            deleteBtn?.setOnClickListener {
                val name = friendNameTextView?.text.toString()
                val dialog = AlertDialog.Builder(context)
                dialog.setMessage(name+"님을 친구목록에서 삭제하시겠습니까?")
                    .setPositiveButton("삭제"){ _, _ ->
                        CoroutineScope(Dispatchers.Main).launch {
                            var deleteFriendToList = async(Dispatchers.IO) {
                                friendApi.deleteFriend(FriendNameData(MyApp.prefs.accountToken!!,name))
                            }
                            Log.d("success",deleteFriendToList.await().success.toString())
                            Log.d("message",deleteFriendToList.await().message)
                            notifyItemRemoved(position)
                        }
                    }
                    .setNegativeButton("취소"){ _, _ ->  }
                    .show()
            }
        }

    }

    fun addItem(item : String){
        items.add(item)
        notifyItemInserted(items.size)
    }

    fun deleteItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun closeSwipeView(){
        mItemManger.closeAllItems()
    }

}
