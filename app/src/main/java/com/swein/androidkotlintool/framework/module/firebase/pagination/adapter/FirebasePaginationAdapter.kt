package com.swein.androidkotlintool.framework.module.firebase.pagination.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.pagination.adapter.item.FirebasePaginationItemViewHolder
import com.swein.androidkotlintool.framework.module.firebase.pagination.model.FirebasePaginationItemModel

class FirebasePaginationAdapter(private val onLoadMore: () -> Unit): RecyclerView.Adapter<FirebasePaginationItemViewHolder>() {

    val list = mutableListOf<FirebasePaginationItemModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FirebasePaginationItemViewHolder {
        return FirebasePaginationItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_firebase_pagination_item, parent, false))
    }

    override fun onBindViewHolder(holder: FirebasePaginationItemViewHolder, position: Int) {

        holder.firebasePaginationItemModel = list[position]
        holder.updateView()

        if (position == list.size - 1) {
            onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: List<FirebasePaginationItemModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: List<FirebasePaginationItemModel>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}