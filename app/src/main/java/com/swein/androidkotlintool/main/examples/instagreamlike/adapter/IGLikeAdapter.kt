package com.swein.androidkotlintool.main.examples.instagreamlike.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.instagreamlike.IGItemModel
import com.swein.androidkotlintool.main.examples.instagreamlike.adapter.item.IGLikeItemViewHolder
import com.swein.androidkotlintool.main.examples.instagreamlike.ig.SpanSize

class IGLikeAdapter: RecyclerView.Adapter<IGLikeItemViewHolder>() {

    private val list = mutableListOf<IGItemModel>()

    var onLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IGLikeItemViewHolder {
        return IGLikeItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_ig_like_item, parent, false))
    }

    override fun onBindViewHolder(holder: IGLikeItemViewHolder, position: Int) {
        holder.model = list[position]



        holder.updateView()

        if (position == list.size - 1) {
            onLoadMore?.let {
                it()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: List<IGItemModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: List<IGItemModel>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}