package com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder

import android.content.Context
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.SHSelectedImageViewHolderAdapter
import com.swein.androidkotlintool.framework.module.shcameraphoto.shselectedimageviewholder.adapter.item.ImageSelectorItemBean
import com.swein.androidkotlintool.framework.util.views.ViewUtil

class SHSelectedImageViewHolder (
    context: Context?,
    private var delegate: SHSelectedImageViewHolderDelegate?,
    private var imageSelectorItemBeanList: MutableList<ImageSelectorItemBean> = mutableListOf()
) {

    companion object {
        const val TAG = "SHSelectedImageViewHolder"
    }

    interface SHSelectedImageViewHolderDelegate {
        fun onDelete(imageSelectorItemBean: ImageSelectorItemBean)
        fun onClose()
    }

    var view: View = ViewUtil.inflateView(context, R.layout.view_holder_sh_selected_image, null)!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: SHSelectedImageViewHolderAdapter
    private lateinit var imageButtonClose: ImageButton

    init {
        findView()
        setListener()
        initList()
    }

    private fun findView() {

        recyclerView = view.findViewById(R.id.recyclerView)
        imageButtonClose = view.findViewById(R.id.imageButtonClose)

    }

    private fun setListener() {
        imageButtonClose.setOnClickListener {
            delegate?.onClose()
        }


//        imageViewClose.setOnClickListener {
//            navigationSimpleViewHolderDelegate?.onClose()
//        }
    }

    private fun initList() {

        linearLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

        adapter = SHSelectedImageViewHolderAdapter(object : SHSelectedImageViewHolderAdapter.SHSelectedImageViewHolderAdapterDelegate {
            override fun onDelete(imageSelectorItemBean: ImageSelectorItemBean) {

                adapter.delete(imageSelectorItemBean)
                delegate?.onDelete(imageSelectorItemBean)

            }
        })

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        reload()
    }

    private fun reload() {
        adapter.reload(imageSelectorItemBeanList)
    }

    fun insert(imageSelectorItemBean: ImageSelectorItemBean) {
        adapter.insert(imageSelectorItemBean)
    }

}