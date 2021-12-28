package com.swein.androidkotlintool.main.examples.overlapviewexample

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.swein.androidkotlintool.R

class OverLapItemView(
    context: Context
): FrameLayout(context) {

    private var textView: TextView
    private var imageView: ShapeableImageView

    init {

        inflate(context, R.layout.view_over_lap_item_view, this)

        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)

        imageView.post{

            imageView.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()

        }

        reset()
    }

    private fun reset() {

        textView.visibility = View.GONE
        imageView.visibility = View.GONE

    }

    fun setImageResource(resource: Int) {

        reset()
        imageView.setImageResource(resource)
        imageView.visibility = View.VISIBLE
    }

    fun setText(text: String) {

        reset()
        textView.text = text
        textView.visibility = View.VISIBLE
    }

    fun overlapToEnd(dpValue: Float) {
        (layoutParams as MarginLayoutParams).setMargins(0, 0, dipToPx(dpValue), 0)
    }

    private fun dipToPx(dipValue: Float): Int {
        return (dipValue * resources.displayMetrics.density).toInt()
    }

}