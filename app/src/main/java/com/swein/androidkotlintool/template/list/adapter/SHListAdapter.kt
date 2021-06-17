package com.swein.androidkotlintool.template.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.toast.ToastUtility
import com.swein.androidkotlintool.template.list.adapter.item.model.ItemDataModel
import com.swein.androidkotlintool.template.list.adapter.item.viewholder.ItemViewHolder

class SHListAdapter: RecyclerView.Adapter<ItemViewHolder> {

    companion object {
        private const val TAG = "SHListAdapter"
    }

    private var mutableList: MutableList<ItemDataModel> = mutableListOf()

    constructor() {}

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

                    itemViewHolder.getItemView()?.context?.let {
                        ILog.debug(TAG, itemDataModel)
                        ToastUtility.showCustomShortToastNormal(it, itemDataModel.toString())
                    }

                }
            })

            itemViewHolder.setItemDataModel(mutableList[p1])
            itemViewHolder.updateView()
        }
    }

    fun loadMore(mutableList: MutableList<ItemDataModel>) {
        this.mutableList.addAll(mutableList)
        notifyItemRangeChanged(this.mutableList.size - mutableList.size + 1, mutableList.size)
    }

    fun reload(mutableList: MutableList<ItemDataModel>) {
        this.mutableList.clear()
        this.mutableList.addAll(mutableList)
        notifyDataSetChanged()
    }




}