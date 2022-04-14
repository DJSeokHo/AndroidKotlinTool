package com.swein.androidkotlintool.main.examples.recyclerviewheaderfooter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.recyclerviewheaderfooter.adapter.item.RHFItemFooterViewHolder
import com.swein.androidkotlintool.main.examples.recyclerviewheaderfooter.adapter.item.RHFItemHeaderViewHolder
import com.swein.androidkotlintool.main.examples.recyclerviewheaderfooter.adapter.item.RHFItemViewHolder

class RHFAdapter(
    private val onLoadMore: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val HEADER_NUMBER = 1
//        private const val FOOTER_NUMBER = 1

        private const val TYPE_ITEM_HEADER = 0
        private const val TYPE_ITEM = 1
//        private const val TYPE_ITEM_FOOTER = 2
    }

    private val list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_ITEM_HEADER -> {
                RHFItemHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_rhf_item_header, parent, false))
            }
//            TYPE_ITEM_FOOTER -> {
//                RHFItemFooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_rhf_item_footer, parent, false))
//            }
            else -> {
                RHFItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_rhf_item, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is RHFItemHeaderViewHolder -> {
                holder.content = "header"
                holder.updateView()
            }

            is RHFItemViewHolder -> {
                holder.content = list[position - HEADER_NUMBER]
                holder.updateView()
            }

            is RHFItemFooterViewHolder -> {
                holder.content = "footer"
                holder.updateView()
            }
        }

        if (position - HEADER_NUMBER == list.size - 1) {
            onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        // count of recycler view items
//        return list.size + HEADER_NUMBER + FOOTER_NUMBER
        return list.size + HEADER_NUMBER
    }

    fun getDataCount(): Int {
        // count of list, this is for pagination
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
        notifyItemRangeChanged(this.list.size - list.size + 1 + HEADER_NUMBER, list.size)
    }

    override fun getItemViewType(position: Int): Int {

        if (0 != HEADER_NUMBER && position < HEADER_NUMBER) {
            // list header
            return TYPE_ITEM_HEADER
        }
//        else if (0 != FOOTER_NUMBER && position >= (HEADER_NUMBER + list.size)) {
//            // list footer
//            return TYPE_ITEM_FOOTER
//        }
        return TYPE_ITEM
    }
}