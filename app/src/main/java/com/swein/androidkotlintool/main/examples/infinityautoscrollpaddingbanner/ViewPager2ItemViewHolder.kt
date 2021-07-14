package com.swein.androidkotlintool.main.examples.infinityautoscrollpaddingbanner

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class

ViewPager2ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view: WeakReference<View> = WeakReference(itemView)

    private lateinit var imageView: ImageView

    var imageResource = 0

    init {
        findView()
    }

    private fun findView() {

        view.get()?.let {
            imageView = it.findViewById(R.id.imageView)
        }
    }

    fun updateView() {
        imageView.setImageResource(imageResource)
    }
}