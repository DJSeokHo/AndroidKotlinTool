package com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview.adapter.item

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class MultipleSelectionRecyclerViewItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var view: WeakReference<View> = WeakReference(itemView)

    private var textView: TextView? = null
    private var imageButtonSelection: ImageButton? = null

    lateinit var data: MultipleSelectionRecyclerViewItemData

    var isSelectionMode = false

    var onSelection: (() -> Unit)? = null

    init {
        findView()
        setListener()
    }

    private fun findView() {
        textView = view.get()?.findViewById(R.id.textView)
        imageButtonSelection = view.get()?.findViewById(R.id.imageButtonSelection)
    }

    private fun setListener() {
        view.get()?.setOnClickListener{

            if (isSelectionMode) {
                data.isSelected = !data.isSelected
                updateSelection()

                onSelection?.let { lambda ->
                    lambda()
                }
            }
            else {
                Toast.makeText(it.context, data.content, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateView() {

        textView?.text = "${data.content}\n\n${data.id}"
        imageButtonSelection?.visibility = if (isSelectionMode) {
            View.VISIBLE
        }
        else {
            View.GONE
        }

        if (!isSelectionMode) {
            // reset
            data.isSelected = false
        }

        updateSelection()
    }

    private fun updateSelection() {
        imageButtonSelection?.setImageResource(if (data.isSelected) {
            R.drawable.ti_check
        }
        else {
            R.drawable.ti_un_check
        })
    }

}