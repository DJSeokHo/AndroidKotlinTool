package com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview.adapter.item

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class TypeCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var view: WeakReference<View> = WeakReference(itemView)

    private var textView: TextView? = null

    var contentC = ""

    init {
        findView()
        setListener()
    }

    private fun findView() {
        textView = view.get()?.findViewById(R.id.textView)
    }

    private fun setListener() {
        view.get()?.setOnClickListener{
            Toast.makeText(it.context, contentC, Toast.LENGTH_SHORT).show()
        }
    }

    fun updateView() {
        textView?.text = contentC
    }

}