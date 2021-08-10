package com.swein.androidkotlintool.main.examples.flowexample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.flowexample.adapter.item.FlowExampleItemViewHolder
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.MVVMRecyclerViewExampleActivity

class FlowExampleAdapter(private val onLoadMore: () -> Unit) : RecyclerView.Adapter<FlowExampleItemViewHolder>() {

    private var list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowExampleItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_flow_example_item, parent, false)
        return FlowExampleItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlowExampleItemViewHolder, position: Int) {

        holder.content = list[position]
        holder.updateView()

        if (position == list.size - 1) {
            onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: List<String>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}