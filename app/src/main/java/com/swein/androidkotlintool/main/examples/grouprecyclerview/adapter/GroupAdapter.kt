package com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.item.GroupItemModel
import com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.item.GroupItemViewHolder

class GroupAdapter(private val onLoadMore: () -> Unit): RecyclerView.Adapter<GroupItemViewHolder>() {

    val list = mutableListOf<GroupItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupItemViewHolder {
        return GroupItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_group_example_item, parent, false))
    }

    override fun onBindViewHolder(holder: GroupItemViewHolder, position: Int) {

        holder.groupItemModel = list[position]
        holder.updateView()

        if (position == list.size - 1) {
            onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: MutableList<GroupItemModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<GroupItemModel>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }

}