package com.swein.androidkotlintool.template.list.adapter.item.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.template.list.adapter.item.model.ItemDataModel
import java.lang.ref.WeakReference

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    interface ItemViewHolderDelegate {
        fun onItemViewHolderClicked(itemDataModel: ItemDataModel)
    }

    private var view: WeakReference<View> = WeakReference(itemView)

    private var textViewTitle: TextView? = null
    private var textViewSubTitle: TextView? = null

    private var itemViewHolderDelegate: ItemViewHolderDelegate? = null
    private var itemDataModel: ItemDataModel? = null

    init {
        findView()
        setListener()
    }

    fun setItemViewHolderDelegate(itemViewHolderDelegate: ItemViewHolderDelegate) {
        this.itemViewHolderDelegate = itemViewHolderDelegate
    }

    fun setItemDataModel(itemDataModel: ItemDataModel) {
        this.itemDataModel = itemDataModel
    }

    private fun findView() {
        textViewTitle = view.get()?.findViewById(R.id.textViewTitle)
        textViewSubTitle = view.get()?.findViewById(R.id.textViewSubTitle)
    }

    private fun setListener() {
        view.get()?.setOnClickListener{
            itemDataModel?.let {
                itemViewHolderDelegate?.onItemViewHolderClicked(it)
            }
        }
    }

    fun updateView() {
        textViewTitle?.text = itemDataModel?.title
        textViewSubTitle?.text = itemDataModel?.subTitle
    }
}