package com.swein.androidkotlintool.main.examples.coordinatorlayoutexample.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class CoordinatorLayoutExampleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var view: WeakReference<View> = WeakReference(itemView)

    private lateinit var textView: TextView

    var content = ""

    init {
        findView()
        setListener()
    }

    private fun findView() {
        view.get()?.let {
            textView = it.findViewById(R.id.textView)
        }
    }

    private fun setListener() {
        view.get()?.setOnClickListener{

        }
    }

    fun updateView() {
        textView.text = content
    }

}