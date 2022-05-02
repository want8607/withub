package com.example.withub.mainFragments.mainFragmentAdapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.R
import kotlinx.android.synthetic.main.github_repositoryadd_fragment.*

class GitHubRepositoryAddFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.github_repositoryadd_fragment, container, false)
        var list = ArrayList<UserRepoData>()
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        list.add(UserRepoData("1","want/withub"))

        var adapter = SignupRecyclerAdapter(list)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : SignupRecyclerAdapter.OnItemClickListener {

            // 삭제 버튼 클릭
            override fun onItemDeleteClick(data: UserRepoData, pos: Int) {
                adapter.deleteItem(pos)
                adapter.notifyItemRemoved(pos)
                adapter.notifyItemRangeChanged(pos, list.size)
            }
        })


        return view
    }
}