package com.swein.androidkotlintool.main.examples.customschedule

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.display.DisplayUtility

@SuppressLint("ViewConstructor")
class ScheduleTimeViewHolder(
    context: Context,
    var timeString: String,
    backgroundColor: Int = Color.TRANSPARENT
): FrameLayout(context) {

    private lateinit var textView: TextView

    init {

        inflate(context, R.layout.view_holder_schedule_time, this)
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtility.dipToPx(context, 42f))

        setBackgroundColor(backgroundColor)

        findView()
        updateView()
    }

    private fun findView() {

        textView = findViewById(R.id.textView)
    }
    fun updateView() {

        textView.text = timeString

    }
}