package com.swein.androidkotlintool.main.examples.recyclerviewheaderfooter.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class RHFItemFooterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)

    private var textView: TextView? = null

    init {

        view.get()?.let {

            textView = it.findViewById(R.id.textView)
        }

    }

    var content = ""

    fun updateView() {

        textView?.text = content
    }
}