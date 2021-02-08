package com.swein.androidkotlintool.framework.module.shcameraphoto.album.selector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.bean.AlbumSelectorItemBean
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.selector.adapter.item.AlbumSelectorItemViewHolder

class AlbumSelectorAdapter(
    private val delegate: AlbumSelectorAdapterDelegate
) : RecyclerView.Adapter<AlbumSelectorItemViewHolder>() {

    interface AlbumSelectorAdapterDelegate {
        fun onLoadMore()
        fun onSelected()
    }

    private var albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AlbumSelectorItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_album_selector_item, p0, false)
        return AlbumSelectorItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return albumSelectorItemBeanList.size
    }

    override fun onBindViewHolder(
        albumSelectorItemViewHolder: AlbumSelectorItemViewHolder,
        position: Int
    ) {

        albumSelectorItemViewHolder.albumSelectorItemBean = albumSelectorItemBeanList[position]
        albumSelectorItemViewHolder.albumSelectorItemViewHolderDelegate = object: AlbumSelectorItemViewHolder.AlbumSelectorItemViewHolderDelegate {
            override fun onSelected() {
                delegate.onSelected()
            }

        }

        albumSelectorItemViewHolder.updateView()

        if (position == albumSelectorItemBeanList.size - 1) {
            delegate.onLoadMore()
        }

    }

    fun reload(albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean>) {
        this.albumSelectorItemBeanList.clear()
        this.albumSelectorItemBeanList.addAll(albumSelectorItemBeanList)
        notifyDataSetChanged()
    }

    fun loadMore(albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean>) {
        this.albumSelectorItemBeanList.addAll(albumSelectorItemBeanList)
        notifyItemRangeChanged(albumSelectorItemBeanList.size - albumSelectorItemBeanList.size + 1,albumSelectorItemBeanList.size)
    }

    override fun onViewRecycled(holder: AlbumSelectorItemViewHolder) {
        holder.clear()
        super.onViewRecycled(holder)
    }

}