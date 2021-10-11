package com.swein.androidkotlintool.main.examples.customschschedule

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.swein.androidkotlintool.R

@SuppressLint("ViewConstructor")
class ScheduleViewHolder(
    context: Context,
    width: Int,
    height: Int,
    title: String,
    member: String,
    backgroundColor: Int
): LinearLayout(context) {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewMember: TextView

    init {

        inflate(context, R.layout.view_holder_schedule, this)
        layoutParams = ViewGroup.LayoutParams(width, height)
        setBackgroundColor(backgroundColor)

        findView()
        updateView(title, member)
    }

    private fun findView() {

        textViewTitle = findViewById(R.id.textViewTitle)
        textViewMember = findViewById(R.id.textViewMember)
    }

    fun updateView(title: String, member: String) {

        textViewTitle.text = title
        textViewMember.text = member

    }
}