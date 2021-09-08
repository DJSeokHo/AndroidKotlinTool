package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.display.DisplayUtility

class CustomTabViewHolder(context: Context): LinearLayout(context) {

    private lateinit var imageView: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewSubTitle: TextView

    init {

        inflate(context, R.layout.view_holder_custom_tab, this)

        findView()

        imageView.visibility = GONE
        textViewTitle.visibility = GONE
        textViewSubTitle.visibility = GONE
    }

    private fun findView() {
        imageView = findViewById(R.id.imageView)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewSubTitle = findViewById(R.id.textViewSubTitle)
    }

    fun setImageResource(resourceId: Int) {
        imageView.setImageResource(resourceId)
        imageView.visibility = VISIBLE
    }

    fun setTitle(title: String) {
        textViewTitle.text = title
        textViewTitle.visibility = VISIBLE
    }

    fun setSubTitle(subTitle: String) {
        textViewSubTitle.text = subTitle
        textViewSubTitle.visibility = VISIBLE
    }

    fun setSelected() {
        if (imageView.visibility == View.VISIBLE) {
            // you can add animation for this
            val layoutParams = imageView.layoutParams
            layoutParams.width = DisplayUtility.dipToPx(context, 40f)
            layoutParams.height = DisplayUtility.dipToPx(context, 40f)
            imageView.layoutParams = layoutParams
        }
        textViewTitle.setTextColor(Color.parseColor("#ee6e61"))
        textViewSubTitle.setTextColor(Color.parseColor("#ee6e61"))
    }

    fun setUnSelected() {
        if (imageView.visibility == View.VISIBLE) {
            // you can add animation for this
            val layoutParams = imageView.layoutParams
            layoutParams.width = DisplayUtility.dipToPx(context, 30f)
            layoutParams.height = DisplayUtility.dipToPx(context, 30f)
            imageView.layoutParams = layoutParams
        }
        textViewTitle.setTextColor(Color.parseColor("#333333"))
        textViewSubTitle.setTextColor(Color.parseColor("#999999"))
    }
}