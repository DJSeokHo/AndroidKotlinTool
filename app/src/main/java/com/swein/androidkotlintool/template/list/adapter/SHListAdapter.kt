package com.swein.androidkotlintool.template.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.template.list.adapter.item.model.ItemDataModel
import com.swein.androidkotlintool.template.list.adapter.item.viewholder.ItemViewHolder

class SHListAdapter: RecyclerView.Adapter<ItemViewHolder> {

    companion object {
        private const val TAG = "SHListAdapter"
    }


    private var mutableList: MutableList<ItemDataModel> = mutableListOf()

    constructor() {
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_sh_list_item, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {
        if(p0 is ItemViewHolder) {

            val itemViewHolder = p0

            itemViewHolder.setItemViewHolderDelegate(object: ItemViewHolder.ItemViewHolderDelegate {
                override fun onItemViewHolderClicked(itemDataModel: ItemDataModel) {
                    ILog.debug(TAG, itemDataModel)
                }
            })

            itemViewHolder.setItemDataModel(mutableList[p1])
            itemViewHolder.updateView()
        }

    }

    fun loadMore() {
        this.mutableList.addAll(createTempData(mutableList.size, 10))
    }

    fun reload() {
        this.mutableList.clear()
        this.mutableList.addAll(createTempData(0, 10))
    }


    private fun createTempData(offset: Int, limit: Int): MutableList<ItemDataModel> {
        val list: MutableList<ItemDataModel> = mutableListOf()

        var itemDataModel: ItemDataModel
        for(i in offset..(offset + limit)) {
            itemDataModel = ItemDataModel("title $i", "sub title $i")
            list.add(itemDataModel)
        }

        return list
    }

}