package com.example.withub.mainFragments.mainFragmentAdapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.example.withub.R

class HomePagerRecyclerAdapter(val context: Context,val requestManager: RequestManager, val imgList : ArrayList<Int>,val urlList : ArrayList<String>) :
    RecyclerView.Adapter<HomePagerRecyclerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tipImg: ImageView = itemView.findViewById(R.id.tip_view)

        fun bind(img: Int,url: String) {
            requestManager
                .load(img)
                .into(tipImg)
            tipImg.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(url))
                context.startActivity(intent)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tip_view,parent,false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(imgList[position%4],urlList[position%4])

    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE

    }
}