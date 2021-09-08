package com.swein.androidkotlintool.main.examples.recyclerviewwithslide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.recyclerviewwithslide.adapter.item.SlideItemViewHolder

class SlideAdapter: RecyclerView.Adapter<SlideItemViewHolder>() {

    private var list = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideItemViewHolder {
        return SlideItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_slide_item, parent, false))
    }

    override fun onBindViewHolder(holder: SlideItemViewHolder, position: Int) {
        holder.index = list[position]

        holder.onDeleteClick = {
            removeItem(it)
        }

        holder.updateView()
    }

    fun reload(list: List<Int>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        list.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

}