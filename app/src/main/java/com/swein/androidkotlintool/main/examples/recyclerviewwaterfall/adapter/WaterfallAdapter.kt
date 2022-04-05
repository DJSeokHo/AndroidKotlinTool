package com.swein.androidkotlintool.main.examples.recyclerviewwaterfall.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.recyclerviewwaterfall.WaterfallItemModel
import com.swein.androidkotlintool.main.examples.recyclerviewwaterfall.adapter.item.WaterfallItemViewHolder

class WaterfallAdapter(

): RecyclerView.Adapter<WaterfallItemViewHolder>() {

    private val list = mutableListOf<WaterfallItemModel>()
    var onLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterfallItemViewHolder {
        return WaterfallItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_waterfall_item, parent, false))
    }

    override fun onBindViewHolder(holder: WaterfallItemViewHolder, position: Int) {

        holder.model = list[position]
        holder.updateView()

        if (position == list.size - 1) {
            onLoadMore?.let {
                it()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: List<WaterfallItemModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: List<WaterfallItemModel>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}