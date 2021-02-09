package com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.item.ImageSelectedItemBean
import com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.item.SHSelectedImageItemViewHolder

class SHSelectedImageViewHolderAdapter(
    private val delegate: SHSelectedImageViewHolderAdapterDelegate
) : RecyclerView.Adapter<SHSelectedImageItemViewHolder>() {

    interface SHSelectedImageViewHolderAdapterDelegate {
        fun onDelete(imageSelectedItemBean: ImageSelectedItemBean)
    }

    private var imageSelectedItemBeanList: MutableList<ImageSelectedItemBean> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SHSelectedImageItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(
            R.layout.view_holder_sh_selected_image_item,
            p0,
            false
        )
        return SHSelectedImageItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageSelectedItemBeanList.size
    }

    override fun onBindViewHolder(selectedImageItemViewHolder: SHSelectedImageItemViewHolder, position: Int) {

        selectedImageItemViewHolder.imageSelectedItemBean = imageSelectedItemBeanList[position]
        selectedImageItemViewHolder.delegate = object: SHSelectedImageItemViewHolder.SHSelectedImageItemViewHolderDelegate {
            override fun onDelete(imageSelectedItemBean: ImageSelectedItemBean) {
                delegate.onDelete(imageSelectedItemBean)
            }
        }
        selectedImageItemViewHolder.updateView()

    }

    fun insert(imageSelectedItemBeanList: ImageSelectedItemBean) {
        this.imageSelectedItemBeanList.add(0, imageSelectedItemBeanList)
        notifyItemInserted(0)
    }

    fun reload(imageSelectedItemBeanList: MutableList<ImageSelectedItemBean>) {
        this.imageSelectedItemBeanList.clear()
        this.imageSelectedItemBeanList.addAll(imageSelectedItemBeanList)
        notifyDataSetChanged()
    }

    fun delete(imageSelectedItemBean: ImageSelectedItemBean) {
        val index = this.imageSelectedItemBeanList.indexOf(imageSelectedItemBean)
        this.imageSelectedItemBeanList.removeAt(index)
        notifyItemRemoved(index)
    }

}