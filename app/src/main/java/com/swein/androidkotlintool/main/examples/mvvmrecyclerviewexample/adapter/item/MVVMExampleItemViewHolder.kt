package com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.adapter.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class MVVMExampleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view: WeakReference<View> = WeakReference(itemView)

    private var textViewIndex: TextView? = null
    private var textViewContent: TextView? = null
    private var imageView: ImageView? = null

    var onItemClick: ((mvvmExampleItemModel: MVVMExampleItemModel) -> Unit)? = null

    var mvvmExampleItemModel: MVVMExampleItemModel? = null

    init {
        findView()
        setListener()
    }

    private fun findView() {
        imageView = view.get()?.findViewById(R.id.imageView)
        textViewIndex = view.get()?.findViewById(R.id.textViewIndex)
        textViewContent = view.get()?.findViewById(R.id.textViewContent)
    }

    private fun setListener() {
        view.get()?.setOnClickListener{

            mvvmExampleItemModel?.let {

                onItemClick?.let { delegate ->
                    delegate(it)
                }
            }
        }
    }

    fun updateView() {

        imageView?.let {
            it.post {

                view.get()?.let { view ->

                    Glide.with(view.context).asBitmap().load(mvvmExampleItemModel?.imageUrl)
                        .transition(BitmapTransitionOptions.withCrossFade())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(it)
                }
            }
        }

        val index = "index is ${mvvmExampleItemModel?.index}"
        textViewIndex?.text = index
        textViewContent?.text = mvvmExampleItemModel?.content
    }
}