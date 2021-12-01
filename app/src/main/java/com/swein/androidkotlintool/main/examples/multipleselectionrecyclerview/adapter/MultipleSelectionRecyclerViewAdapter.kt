package com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview.adapter.item.MultipleSelectionRecyclerViewItemData
import com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview.adapter.item.MultipleSelectionRecyclerViewItemViewHolder

class MultipleSelectionRecyclerViewAdapter: RecyclerView.Adapter<MultipleSelectionRecyclerViewItemViewHolder>() {

    private var list: MutableList<MultipleSelectionRecyclerViewItemData> = mutableListOf()

    var onLoadMore: (() -> Unit)? = null

    private var isSelectionMode: Boolean = false

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MultipleSelectionRecyclerViewItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_multiple_selection_recycler_view_item, p0, false)
        return  MultipleSelectionRecyclerViewItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MultipleSelectionRecyclerViewItemViewHolder, p1: Int) {

        p0.data = list[p1]
        p0.isSelectionMode = isSelectionMode
        p0.onSelection = {

            for (data in list) {
                if (data.isSelected) {
                    ILog.debug("???", "selected data is ${data.content} ${data.id}")
                }
            }
        }
        p0.updateView()

        if (p1 == list.size - 1) {
            onLoadMore?.let {
                it()
            }
        }
    }

    fun reload(list: MutableList<MultipleSelectionRecyclerViewItemData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<MultipleSelectionRecyclerViewItemData>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }

    fun toggleSelection(isSelectionMode: Boolean) {
        this.isSelectionMode = isSelectionMode
        notifyDataSetChanged()
    }
}