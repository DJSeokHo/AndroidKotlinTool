package com.swein.androidkotlintool.framework.module.firebase.pagination.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.pagination.model.FirebasePaginationItemModel
import java.lang.ref.WeakReference

class FirebasePaginationItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)

    var firebasePaginationItemModel: FirebasePaginationItemModel? = null

    private var textViewTitle: TextView? = null
    private var textViewMessage: TextView? = null
    private var textViewCreateDate: TextView? = null
    private var textViewTag: TextView? = null


    init {
        findView()
    }

    private fun findView() {

        view.get()?.let {
            textViewTitle = it.findViewById(R.id.textViewTitle)
            textViewMessage = it.findViewById(R.id.textViewMessage)
            textViewCreateDate = it.findViewById(R.id.textViewCreateDate)
            textViewTag = it.findViewById(R.id.textViewTag)
        }
    }

    fun updateView() {
        textViewTitle?.text = firebasePaginationItemModel?.title
        textViewMessage?.text = firebasePaginationItemModel?.message
        textViewCreateDate?.text = firebasePaginationItemModel?.createDate
        textViewTag?.text = firebasePaginationItemModel?.tag
    }
}