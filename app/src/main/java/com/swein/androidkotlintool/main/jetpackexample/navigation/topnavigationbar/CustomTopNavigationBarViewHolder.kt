package com.swein.androidkotlintool.main.jetpackexample.navigation.topnavigationbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.swein.androidkotlintool.R

class CustomTopNavigationBarViewHolder(
    context: Context, root: ViewGroup,
    val view: View = LayoutInflater.from(context).inflate(R.layout.view_holder_custom_top_navigation_bar, root),
    val onImageButtonStartClick: (() -> Unit)? = null,
    val onImageButtonEndClick: (() -> Unit)? = null
) {

    private lateinit var textView: TextView
    private lateinit var imageButtonStart: ImageButton
    private lateinit var imageButtonEnd: ImageButton

    init {
        findView()
        setListener()
    }

    private fun findView() {
        textView = view.findViewById(R.id.textView)
        imageButtonStart = view.findViewById(R.id.imageButtonStart)
        imageButtonEnd = view.findViewById(R.id.imageButtonEnd)
    }

    private fun setListener() {
        imageButtonStart.setOnClickListener {
            onImageButtonStartClick?.let { lambda ->
                lambda()
            }
        }
        imageButtonEnd.setOnClickListener {
            onImageButtonEndClick?.let { lambda ->
                lambda()
            }
        }
    }

    fun toggleStartClick(imageDrawable: Drawable? = ContextCompat.getDrawable(view.context, R.mipmap.ti_back_white), enableClick: Boolean = true) {
        imageButtonStart.setImageDrawable(imageDrawable)
        imageButtonStart.isClickable = enableClick
    }

    fun toggleEndClick(imageDrawable: Drawable? = ContextCompat.getDrawable(view.context, R.mipmap.ti_close_white), enableClick: Boolean = true) {
        imageButtonEnd.setImageDrawable(imageDrawable)
        imageButtonEnd.isClickable = enableClick
    }

    fun setTitle(title: String) {
        textView.text = title
    }
}