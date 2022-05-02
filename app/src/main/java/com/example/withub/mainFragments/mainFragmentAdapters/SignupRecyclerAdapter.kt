package com.example.withub.mainFragments.mainFragmentAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.R
import kotlinx.android.synthetic.main.signup_repository_recyclerview.view.*

class SignupRecyclerAdapter (private val items: ArrayList<UserRepoData>) : RecyclerView.Adapter<SignupRecyclerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemDeleteClick(data: UserRepoData, pos: Int)
    }

    private var clicklistener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clicklistener = listener
    }


    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].num.toInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view: View?
//        return when (viewType) {
//                item_type1 -> {
//                    view = LayoutInflater.from(parent.context).inflate(
//                        R.layout.signup_repository_recyclerview,
//                        parent,
//                        false
//                    )
//                    MultiViewHolder1(view)
//                }
//        }

        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.signup_repository_recyclerview, parent, false)
        return SignupRecyclerAdapter.ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: SignupRecyclerAdapter.ViewHolder, position: Int) {

//        holder.run {
//            itemView.tag = items[position]
//
//            itemView.btnDelete?.setOnClickListener {
//                clickListener?.onItemDeleteClick(items[position], position)
//            }
//        }
//
//        when (items[position].num) {
//            item_type1 -> {
//                (holder as MultiViewHolder1).bind(items[position])
//            }
//        }

    }

    fun deleteLoading() {
        items.removeAt(items.lastIndex)
    }

    inner class MultiViewHolder1(view: View) : RecyclerView.ViewHolder(view) {

        private var mview: View = view

        fun bind(item: UserRepoData) {
            mview.name_repository_num.text = item.num
            mview.name_repository.text = item.nameRepository
        }
    }

    inner class MultiViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        private var mview: View = view
        fun bind(item: UserRepoData) {
            mview.name_repository_num.text = item.num
            mview.name_repository.text = item.nameRepository
        }
    }

    inner class MultiViewHolder3(view: View) : RecyclerView.ViewHolder(view) {
        private var mview: View = view
        fun bind(item: UserRepoData) {
            mview.name_repository.text = item.nameRepository
        }
    }


    fun deleteItem(pos: Int) {
        items.removeAt(pos)
    }




    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: UserRepoData) {
            view.name_repository_num.text = item.num
            view.name_repository.text = item.nameRepository
            view.setOnClickListener(listener)
        }
    }
}