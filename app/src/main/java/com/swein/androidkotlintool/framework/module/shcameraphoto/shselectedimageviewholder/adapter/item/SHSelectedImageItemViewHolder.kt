package com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import io.everyportable.framework.util.glide.SHGlide
import java.lang.ref.WeakReference

class SHSelectedImageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    interface SHSelectedImageItemViewHolderDelegate {
        fun onDelete(imageSelectorItemBean: ImageSelectorItemBean)
    }

    var view: WeakReference<View> = WeakReference(itemView)

    lateinit var imageView: ImageView
    lateinit var textViewDelete: TextView

    lateinit var imageSelectorItemBean: ImageSelectorItemBean

    var delegate: SHSelectedImageItemViewHolderDelegate? = null

    init {

        view = WeakReference(itemView)

        findView()
        setListener()
    }


    private fun findView() {

        imageView = view.get()!!.findViewById(R.id.imageView)
        textViewDelete = view.get()!!.findViewById(R.id.textViewDelete)

    }

    private fun setListener() {

        imageView.setOnClickListener {

        }

        textViewDelete.setOnClickListener {
            delegate?.onDelete(imageSelectorItemBean)
        }

    }

    fun updateView() {

        view.get()?.let {

            imageView.post {
                SHGlide.setImageFilePath(it.context, imageSelectorItemBean.filePath, imageView, null, imageView.width, imageView.height, 0f, 0f)
            }
        }
    }
}