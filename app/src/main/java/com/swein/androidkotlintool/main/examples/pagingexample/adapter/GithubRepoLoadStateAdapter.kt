package com.swein.androidkotlintool.main.examples.pagingexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.pagingexample.adapter.item.GithubRepoFooterItemViewHolder

class GithubRepoLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<GithubRepoFooterItemViewHolder>() {

    override fun onBindViewHolder(holder: GithubRepoFooterItemViewHolder, loadState: LoadState) {
        holder.retry = retry

        when (loadState) {

            is LoadState.Error -> {
                holder.stateError(loadState.error.localizedMessage ?: "error")
            }

            is LoadState.Loading -> {
                holder.stateLoading()
            }

            else -> {
                holder.stateNone()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GithubRepoFooterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_github_example_footer_item, parent, false)
        return GithubRepoFooterItemViewHolder(view)
    }
}