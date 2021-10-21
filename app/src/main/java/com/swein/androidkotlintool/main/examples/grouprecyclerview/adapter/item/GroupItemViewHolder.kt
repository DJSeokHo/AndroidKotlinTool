package com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class GroupItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val view = WeakReference(itemView)

    private var textViewTitle: TextView? = null
    private var textViewDate: TextView? = null

    var groupItemModel: GroupItemModel? = null

    init {

        view.get()?.let {

            textViewTitle = it.findViewById(R.id.textViewTitle)
            textViewDate = it.findViewById(R.id.textViewDate)

        }
    }

    fun updateView() {

        textViewTitle?.text = groupItemModel?.title
        textViewDate?.text = groupItemModel?.date
    }

}