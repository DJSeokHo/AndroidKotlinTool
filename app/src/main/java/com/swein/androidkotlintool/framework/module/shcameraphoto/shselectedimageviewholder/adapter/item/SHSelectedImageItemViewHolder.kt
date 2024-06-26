package com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.glide.SHGlide
import java.lang.ref.WeakReference

class SHSelectedImageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    interface SHSelectedImageItemViewHolderDelegate {
        fun onDelete(imageSelectedItemBean: ImageSelectedItemBean)
    }

    var view: WeakReference<View> = WeakReference(itemView)

    lateinit var imageView: ImageView
    lateinit var textViewDelete: TextView

    lateinit var imageSelectedItemBean: ImageSelectedItemBean

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
            delegate?.onDelete(imageSelectedItemBean)
        }

    }

    fun updateView() {

        view.get()?.let {

            imageView.post {
                SHGlide.setImage(imageView, width = imageView.width, height = imageView.height, uri = imageSelectedItemBean.imageUri)
            }
        }
    }
}