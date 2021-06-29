package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class MDDaySevenItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view: WeakReference<View> = WeakReference(itemView)

    private var textView: TextView? = null

    var content = ""

    init {
        findView()
    }

    private fun findView() {
        textView = view.get()?.findViewById(R.id.textView)
    }

    fun updateView() {
        textView?.text = content
    }
}