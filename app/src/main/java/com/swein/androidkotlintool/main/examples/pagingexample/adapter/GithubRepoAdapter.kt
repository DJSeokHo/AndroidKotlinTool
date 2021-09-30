package com.swein.androidkotlintool.main.examples.pagingexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.pagingexample.adapter.item.GithubRepoItemViewHolder
import com.swein.androidkotlintool.main.examples.pagingexample.model.GithubRepoItemModel

class GithubRepoAdapter: PagingDataAdapter<GithubRepoItemModel, GithubRepoItemViewHolder>(

    object : DiffUtil.ItemCallback<GithubRepoItemModel>() {
        override fun areItemsTheSame(
            oldItem: GithubRepoItemModel,
            newItem: GithubRepoItemModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GithubRepoItemModel,
            newItem: GithubRepoItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }

) {

    override fun onBindViewHolder(holder: GithubRepoItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.modelRepo = it
            holder.updateView()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_github_example_item, parent, false)
        return GithubRepoItemViewHolder(view)
    }

}