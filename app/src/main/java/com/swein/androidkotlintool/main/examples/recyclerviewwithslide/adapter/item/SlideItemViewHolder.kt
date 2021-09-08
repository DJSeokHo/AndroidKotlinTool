package com.swein.androidkotlintool.main.examples.recyclerviewwithslide.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class SlideItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)
    private lateinit var textView: TextView
    private lateinit var textViewDelete: TextView

    var onDeleteClick: ((RecyclerView.ViewHolder) -> Unit)? = null

    var index = 0

    init {
        view.get()?.let {

            it.setOnClickListener {

                // reset swiped position
                if (view.get()?.scrollX != 0) {
                    view.get()?.scrollTo(0, 0)
                }
            }

            textView = it.findViewById(R.id.textView)
            textViewDelete = it.findViewById(R.id.textViewDelete)

            textViewDelete.setOnClickListener {
                onDeleteClick?.let { onDeleteClick ->
                    onDeleteClick(this)
                }
            }
        }
    }

    fun updateView() {
        // must reset swiped position because item is reused
        view.get()?.scrollTo(0, 0)
        textView.text = "index $index"
    }
}