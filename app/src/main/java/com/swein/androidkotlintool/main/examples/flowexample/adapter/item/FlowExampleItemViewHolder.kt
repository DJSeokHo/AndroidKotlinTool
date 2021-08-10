package com.swein.androidkotlintool.main.examples.flowexample.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class FlowExampleItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view: WeakReference<View> = WeakReference(itemView)

    private lateinit var textView: TextView

    var content = ""

    init {
        findView()
    }

    private fun findView() {

        view.get()?.let {
            textView = it.findViewById(R.id.textView)
        }
    }

    fun updateView() {
        textView.text = content
    }

}