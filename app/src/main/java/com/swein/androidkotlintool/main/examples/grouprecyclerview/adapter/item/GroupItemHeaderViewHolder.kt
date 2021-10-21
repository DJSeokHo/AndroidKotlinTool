package com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.item

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import com.swein.androidkotlintool.R

@SuppressLint("ViewConstructor")
class GroupItemHeaderViewHolder(
    context: Context
): FrameLayout(context) {

    private lateinit var textViewDate: TextView

    init {

        inflate(context, R.layout.view_holder_group_example_header_item, this)

        findView()

    }

    private fun findView() {
        textViewDate = findViewById(R.id.textViewDate)
    }

    fun setDate(dateString: String) {
        textViewDate.text = dateString
    }
}