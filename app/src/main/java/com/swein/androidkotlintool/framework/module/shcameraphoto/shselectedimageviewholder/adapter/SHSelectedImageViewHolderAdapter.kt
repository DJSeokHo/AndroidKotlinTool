package com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.item.ImageSelectorItemBean
import com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.item.SHSelectedImageItemViewHolder

class SHSelectedImageViewHolderAdapter(
    private val delegate: SHSelectedImageViewHolderAdapterDelegate
) : RecyclerView.Adapter<SHSelectedImageItemViewHolder>() {

    interface SHSelectedImageViewHolderAdapterDelegate {
        fun onDelete(imageSelectorItemBean: ImageSelectorItemBean)
    }

    private var imageSelectorItemBeanList: MutableList<ImageSelectorItemBean> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SHSelectedImageItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(
            R.layout.view_holder_sh_selected_image_item,
            p0,
            false
        )
        return SHSelectedImageItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageSelectorItemBeanList.size
    }

    override fun onBindViewHolder(selectedImageItemViewHolder: SHSelectedImageItemViewHolder, position: Int) {

        selectedImageItemViewHolder.imageSelectorItemBean = imageSelectorItemBeanList[position]
        selectedImageItemViewHolder.delegate = object: SHSelectedImageItemViewHolder.SHSelectedImageItemViewHolderDelegate {
            override fun onDelete(imageSelectorItemBean: ImageSelectorItemBean) {
                delegate.onDelete(imageSelectorItemBean)
            }
        }
        selectedImageItemViewHolder.updateView()

    }

    fun insert(imageSelectorItemBeanList: ImageSelectorItemBean) {
        this.imageSelectorItemBeanList.add(0, imageSelectorItemBeanList)
        notifyItemInserted(0)
    }

    fun reload(imageSelectorItemBeanList: MutableList<ImageSelectorItemBean>) {
        this.imageSelectorItemBeanList.clear()
        this.imageSelectorItemBeanList.addAll(imageSelectorItemBeanList)
        notifyDataSetChanged()
    }

    fun delete(imageSelectorItemBean: ImageSelectorItemBean) {
        val index = this.imageSelectorItemBeanList.indexOf(imageSelectorItemBean)
        this.imageSelectorItemBeanList.removeAt(index)
        notifyItemRemoved(index)
    }

}