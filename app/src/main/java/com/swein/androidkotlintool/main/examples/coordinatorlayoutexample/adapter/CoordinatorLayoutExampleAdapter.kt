package com.swein.androidkotlintool.main.examples.coordinatorlayoutexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.coordinatorlayoutexample.adapter.item.CoordinatorLayoutExampleItemViewHolder

class CoordinatorLayoutExampleAdapter:
    RecyclerView.Adapter<CoordinatorLayoutExampleItemViewHolder>() {

    private var list: MutableList<String> = mutableListOf()

    var onLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CoordinatorLayoutExampleItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_coordinator_layout_example_item, p0, false)
        return CoordinatorLayoutExampleItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: CoordinatorLayoutExampleItemViewHolder, p1: Int) {
        p0.content = list[p1]
        p0.updateView()
        
        if (p1 == list.size - 1) {
            onLoadMore?.let {
                it()
            }
        }
    }

    fun reload(list: MutableList<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<String>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}