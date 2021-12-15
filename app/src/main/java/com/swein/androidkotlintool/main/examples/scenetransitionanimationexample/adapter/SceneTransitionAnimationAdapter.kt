package com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.adapter.item.SceneTransitionAnimationItemModel
import com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.adapter.item.SceneTransitionAnimationItemViewHolder

class SceneTransitionAnimationAdapter(
    private val onItemClick: ((model: SceneTransitionAnimationItemModel, imageView: ImageView, textView: TextView) -> Unit)? = null
): RecyclerView.Adapter<SceneTransitionAnimationItemViewHolder>() {

    private val modelList = mutableListOf<SceneTransitionAnimationItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneTransitionAnimationItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_scene_transition_animation_item, parent, false)
        return SceneTransitionAnimationItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SceneTransitionAnimationItemViewHolder, position: Int) {
        holder.model = modelList[position]
        holder.onItemClick = onItemClick
        holder.updateView()
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(modelList: MutableList<SceneTransitionAnimationItemModel>) {
        this.modelList.clear()
        this.modelList.addAll(modelList)
        notifyDataSetChanged()
    }
}
