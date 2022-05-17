package com.example.withub.mainFragments.mainFragmentAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.R


class ExpandableRVAdapter( var rankingDataList: ArrayList<List<FriendRankingData>>):RecyclerView.Adapter<ExpandableRVAdapter.Holder>() {
    var visibilityList = arrayListOf<Boolean>(false,false,false,false)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ranking_recycler_view_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return rankingDataList.size
    }

    inner class Holder(itemView: View?):RecyclerView.ViewHolder(itemView!!){

        var expandableLayout : LinearLayout? = itemView?.findViewById(R.id.expandable_layout)
        val cardView = itemView?.findViewById<ConstraintLayout>(R.id.cardView_content)
        val cardViewTitle = itemView?.findViewById<TextView>(R.id.cardView_title)
        val friendName1 = itemView?.findViewById<TextView>(R.id.friend_name_1)
        val friendName2 = itemView?.findViewById<TextView>(R.id.friend_name_2)
        val friendName3 = itemView?.findViewById<TextView>(R.id.friend_name_3)
        val friendName4 = itemView?.findViewById<TextView>(R.id.friend_name_4)
        val friendName5 = itemView?.findViewById<TextView>(R.id.friend_name_5)
        val friendName6 = itemView?.findViewById<TextView>(R.id.friend_name_6)
        val friendName7 = itemView?.findViewById<TextView>(R.id.friend_name_7)
        val friendName8 = itemView?.findViewById<TextView>(R.id.friend_name_8)
        val friendName9 = itemView?.findViewById<TextView>(R.id.friend_name_9)
        val friendName10 = itemView?.findViewById<TextView>(R.id.friend_name_10)
        val friendCommitNum1 = itemView?.findViewById<TextView>(R.id.friend_commit_num_1)
        val friendCommitNum2 = itemView?.findViewById<TextView>(R.id.friend_commit_num_2)
        val friendCommitNum3 = itemView?.findViewById<TextView>(R.id.friend_commit_num_3)
        val friendCommitNum4 = itemView?.findViewById<TextView>(R.id.friend_commit_num_4)
        val friendCommitNum5 = itemView?.findViewById<TextView>(R.id.friend_commit_num_5)
        val friendCommitNum6 = itemView?.findViewById<TextView>(R.id.friend_commit_num_6)
        val friendCommitNum7 = itemView?.findViewById<TextView>(R.id.friend_commit_num_7)
        val friendCommitNum8 = itemView?.findViewById<TextView>(R.id.friend_commit_num_8)
        val friendCommitNum9 = itemView?.findViewById<TextView>(R.id.friend_commit_num_9)
        val friendCommitNum10 = itemView?.findViewById<TextView>(R.id.friend_commit_num_10)

        fun bind(position: Int){
            Log.d("mess","실행")
            val isExpandable : Boolean = visibilityList[position]
            expandableLayout?.visibility = if(isExpandable) View.VISIBLE else View.GONE
            cardViewTitle?.text = itemView.resources.getStringArray(R.array.ranking_title)[position]
            friendName1?.text = rankingDataList[position][0].name
            friendName2?.text = rankingDataList[position][1].name
            friendName3?.text = rankingDataList[position][2].name
            friendName4?.text = rankingDataList[position][3].name
            friendName5?.text = rankingDataList[position][4].name
            friendName6?.text = rankingDataList[position][5].name
            friendName7?.text = rankingDataList[position][6].name
            friendName8?.text = rankingDataList[position][7].name
            friendName9?.text = rankingDataList[position][8].name
            friendName10?.text = rankingDataList[position][9].name
            friendCommitNum1?.text = rankingDataList[position][0].commitNum.toString()
            friendCommitNum2?.text = rankingDataList[position][1].commitNum.toString()
            friendCommitNum3?.text = rankingDataList[position][2].commitNum.toString()
            friendCommitNum4?.text = rankingDataList[position][3].commitNum.toString()
            friendCommitNum5?.text = rankingDataList[position][4].commitNum.toString()
            friendCommitNum6?.text = rankingDataList[position][5].commitNum.toString()
            friendCommitNum7?.text = rankingDataList[position][6].commitNum.toString()
            friendCommitNum8?.text = rankingDataList[position][7].commitNum.toString()
            friendCommitNum9?.text = rankingDataList[position][8].commitNum.toString()
            friendCommitNum10?.text = rankingDataList[position][9].commitNum.toString()
            cardView?.setOnClickListener {
                var viewVisibility = visibilityList[position]
                 visibilityList[position] = !viewVisibility
                notifyItemChanged(position)
            }
        }
    }
}