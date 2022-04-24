package com.example.withub.mainFragments.mainFragmentAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.R

class HomePagerRecyclerAdapter(val textList : ArrayList<String>) :
    RecyclerView.Adapter<HomePagerRecyclerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pageText: TextView = itemView.findViewById(R.id.tip_text)

        fun bind(dd: String) {
            pageText.text = dd
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tip_view,parent,false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(textList[position%4])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE

    }
}