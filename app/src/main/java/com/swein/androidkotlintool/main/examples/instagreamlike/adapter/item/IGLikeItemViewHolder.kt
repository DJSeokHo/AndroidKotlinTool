package com.swein.androidkotlintool.main.examples.instagreamlike.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.display.DisplayUtility
import com.swein.androidkotlintool.main.examples.instagreamlike.IGItemModel
import java.lang.ref.WeakReference

class IGLikeItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)

    private var imageView: ShapeableImageView? = null
    private var textView: TextView? = null

    var model: IGItemModel? = null

    init {

        view.get()?.let {
            textView = it.findViewById(R.id.textView)
            imageView = it.findViewById(R.id.imageView)

//            imageView?.let { imageView ->
//                val radius = DisplayUtility.dipToPx(imageView.context, 50f)
//                imageView.shapeAppearanceModel = imageView.shapeAppearanceModel.toBuilder()
//                    .setAllCorners(CornerFamily.ROUNDED, radius.toFloat())
//                    .build()
//            }

        }

    }

    fun updateView() {

        model?.let { model ->
            textView?.text = model.title

            imageView?.let {
                Glide.with(it.context).asBitmap().load(model.imageUrl)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(it)
            }

        }

    }

}