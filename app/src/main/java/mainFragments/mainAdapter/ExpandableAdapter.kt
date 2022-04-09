package com.example.withub.mainFragments.mainAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.R

class ExpandableAdapter(var context: Context) : RecyclerView.Adapter<ExpandableAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.ranking_recycler_view_item, parent, false)
        Log.d("dklddk","dkdlsk")
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){

        var expandableLayout : LinearLayout? = itemView?.findViewById(R.id.expandable_layout)
//        val isExpandable : Boolean = visibilityList[absoluteAdapterPosition]
        var cardView = itemView?.findViewById<ConstraintLayout>(R.id.cardView_content)
        var cardViewTitle = itemView?.findViewById<TextView>(R.id.cardView_title)

        fun bind(position: Int){
            Log.d("mess","실행")
//            expandableLayout?.visibility = if(isExpandable) View.VISIBLE else View.GONE
            cardViewTitle?.text = "일간 커밋 횟수"
            cardView?.setOnClickListener {
//                var viewVisibility = visibilityList[position]
//                viewVisibility = !viewVisibility
                notifyItemChanged(position)
            }
        }
    }
}