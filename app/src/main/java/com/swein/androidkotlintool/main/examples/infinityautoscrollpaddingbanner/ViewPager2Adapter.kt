package com.swein.androidkotlintool.main.examples.infinityautoscrollpaddingbanner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R

class ViewPager2Adapter : RecyclerView.Adapter<ViewPager2ItemViewHolder>() {

    private val list = mutableListOf<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPager2ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_view_pager2_item, parent, false)
        return ViewPager2ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPager2ItemViewHolder, position: Int) {
        holder.imageResource = list[position]
        holder.updateView()
    }

    fun reload(list: MutableList<Int>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}