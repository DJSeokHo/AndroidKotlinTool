package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven.adapter.item.MDDaySevenItemViewHolder

class MDDaySevenAdapter: RecyclerView.Adapter<MDDaySevenItemViewHolder>() {

    var onLoadMore: (() -> Unit)? = null

    var list = mutableListOf<String>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MDDaySevenItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_holder_md_day_seven_item, viewGroup, false)
        return MDDaySevenItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: MDDaySevenItemViewHolder, index: Int) {

        viewHolder.content = list[index]
        viewHolder.updateView()

        if (index == list.size - 1) {
            onLoadMore?.let {
                it()
            }
        }
    }

    fun reload(list: MutableList<String>) {
        ILog.debug("Adapter", "reload")
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<String>) {
        this.list.addAll(list)
        ILog.debug("Adapter", "loadMore")
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }

}