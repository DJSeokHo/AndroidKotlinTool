package com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.adapter.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import java.lang.ref.WeakReference

class SceneTransitionAnimationItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    var onItemClick: ((model: SceneTransitionAnimationItemModel, imageView: ImageView, textView: TextView) -> Unit)? = null
    var model: SceneTransitionAnimationItemModel? = null

    init {
        findView()

        view.get()?.setOnClickListener {

            onItemClick?.let { onItemClick ->
                model?.let { model ->
                    onItemClick(model, imageView, textView)
                }

            }

        }
    }

    private fun findView() {

        view.get()?.let {
            imageView = it.findViewById(R.id.imageView)
            textView = it.findViewById(R.id.textView)
        }

    }

    fun updateView() {

        model?.let {
            imageView.setImageResource(it.imageResource)
            textView.text = it.contentString
        }

    }

    protected fun finalize() {
        ILog.debug("???", "finalize")
    }
}