package com.swein.androidkotlintool.main.examples.googlemapexample.popup

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.swein.androidkotlintool.R

@SuppressLint("ViewConstructor")
class GoogleMapPopupView(
    context: Context,
    bitmap: Bitmap,
    info: String,
): ConstraintLayout(context) {

    private var imageView: ImageView
    private var textView: TextView

    init {
        inflate(context, R.layout.view_google_map_popup, this)

        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)

        textView.text = info
        imageView.setImageBitmap(bitmap)
        // you can also set url to image with Glide open source
    }
}