package com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.toast.ToastUtil
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.MVVMRecyclerViewExampleActivity
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.adapter.item.MVVMExampleItemModel
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.adapter.item.MVVMExampleItemViewHolder

class MVVMExampleAdapter(private var list: MutableList<MVVMExampleItemModel>?): RecyclerView.Adapter<MVVMExampleItemViewHolder>() {

    var onLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MVVMExampleItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_holder_mvvm_example_item, viewGroup, false)
        return MVVMExampleItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: MVVMExampleItemViewHolder, index: Int) {

        list?.let { list ->

            viewHolder.mvvmExampleItemModel = list[index]
            viewHolder.onItemClick = { model ->
                ToastUtil.showCustomShortToastNormal(viewHolder.itemView.context, "${model.index} ${model.content}")
            }

            viewHolder.updateView()

            if (index == list.size - 1) {
                onLoadMore?.let {
                    it()
                }
            }
        }
    }

    fun reload() {
        notifyDataSetChanged()
    }

    fun loadMore() {
        this.list?.let {
            notifyItemRangeChanged(it.size - MVVMRecyclerViewExampleActivity.LIST_LIMIT + 1, MVVMRecyclerViewExampleActivity.LIST_LIMIT)
        }
    }

}