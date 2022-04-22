package com.swein.androidkotlintool.main.examples.pdfreader.adapter.item

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class PDFReaderItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val view = WeakReference(itemView)

    var textView: TextView? = null
    var imageView: PhotoView? = null

    var bitmap: Bitmap? = null

    var page: Int = 0

    init {

        view.get()?.let {

            textView = it.findViewById(R.id.textView)
            imageView = it.findViewById(R.id.imageView)
        }
    }

    fun updateView() {

        imageView?.setImageBitmap(bitmap)

        @SuppressLint("SetTextI18n")
        textView?.text = "${page + 1}"
    }

}