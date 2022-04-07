package com.example.withub.mainFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.withub.R

class PagerRecyclerAdapter(val textList : ArrayList<String>) :
    RecyclerView.Adapter<PagerRecyclerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pageText: TextView = itemView.findViewById(R.id.tip_text)

        fun bind(position: Int) {
            pageText.text = textList[position]
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerRecyclerAdapter.PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tip_view,parent,false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerRecyclerAdapter.PagerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return textList.size

    }
}