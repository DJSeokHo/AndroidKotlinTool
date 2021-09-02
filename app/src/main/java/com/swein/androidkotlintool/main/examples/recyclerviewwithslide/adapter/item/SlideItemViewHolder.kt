package com.swein.androidkotlintool.main.examples.recyclerviewwithslide.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class SlideItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)
    private lateinit var textView: TextView
//    private lateinit var textViewDelete: TextView

    var index = 0

    init {
        view.get()?.let {
            textView = it.findViewById(R.id.textView)
//            textViewDelete = it.findViewById(R.id.textViewDelete)
//
//            textViewDelete.setOnClickListener {
//
//            }
        }
    }

    fun updateView() {
        textView.text = "index $index"
    }
}