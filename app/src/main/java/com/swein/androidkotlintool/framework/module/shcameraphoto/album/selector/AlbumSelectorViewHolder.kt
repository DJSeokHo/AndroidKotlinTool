package com.swein.androidkotlintool.framework.module.shcameraphoto.album.selector

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.AlbumSelectorWrapper
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.bean.AlbumFolderItemBean
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.bean.AlbumSelectorItemBean
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.selector.adapter.AlbumSelectorAdapter
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.framework.util.views.ViewUtil
import java.util.*

class AlbumSelectorViewHolder(
    context: Context,
    private val maxSelect: Int,
    private val delegate: AlbumSelectorViewHolderDelegate
) {

    companion object {
        private const val TAG = "AlbumSelectorViewHolder"

    }

    interface AlbumSelectorViewHolderDelegate {
        fun onInitFinish()
        fun onConfirm()
        fun onClose()
    }

    val view: View = ViewUtil.inflateView(context, R.layout.view_holder_album_selector, null)!!

    private lateinit var albumSelectorAdapter: AlbumSelectorAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var textViewAction: TextView
    private lateinit var textViewSelected: TextView

    private val albumFolderItemBeanList: MutableList<AlbumFolderItemBean> = mutableListOf()
    private val albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean> = mutableListOf()

    private var selectedList: MutableList<AlbumSelectorItemBean> = mutableListOf()

    init {
        initData()
        findView()
        setListener()
    }

    private fun findView() {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        textViewAction = view.findViewById(R.id.textViewAction)
        textViewSelected = view.findViewById(R.id.textViewSelected)
    }

    private fun setListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            reload()
        }

        textViewAction.setOnClickListener {

            if(selectedList.isEmpty()) {
                delegate.onClose()
            }
            else {
                delegate.onConfirm()
            }
        }
    }

    private fun initData() {
        AlbumSelectorWrapper.scanImageFile(
            view.context,
            object : AlbumSelectorWrapper.AlbumSelectorWrapperDelegate {

                override fun onSuccess(
                    albumFolderItemBeanList: MutableList<AlbumFolderItemBean>,
                    albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean>
                ) {

                    this@AlbumSelectorViewHolder.albumFolderItemBeanList.clear()
                    this@AlbumSelectorViewHolder.albumFolderItemBeanList.addAll(
                        albumFolderItemBeanList
                    )
                    this@AlbumSelectorViewHolder.albumSelectorItemBeanList.clear()
                    this@AlbumSelectorViewHolder.albumSelectorItemBeanList.addAll(
                        albumSelectorItemBeanList
                    )

                    initList()
                    delegate.onInitFinish()
                }

                override fun onError() {
                    ILog.debug(TAG, "error")
                }

            })
    }

    private fun initList() {
        layoutManager = GridLayoutManager(view.context, 3)
        albumSelectorAdapter = AlbumSelectorAdapter(object :
            AlbumSelectorAdapter.AlbumSelectorAdapterDelegate {
            override fun onLoadMore() {
                loadMore()
            }

            override fun onSelected() {
                selectedList.clear()

                for (i in albumSelectorItemBeanList.indices) {
                    if (albumSelectorItemBeanList[i].isSelected) {
                        selectedList.add(albumSelectorItemBeanList[i])
                    }
                }

                for (i in selectedList.indices) {
                    ILog.debug(
                        TAG,
                        "${selectedList[i].filePath} ${selectedList[i].isSelected}"
                    )
                }

                if (selectedList.size >= maxSelect) {
                    EventCenter.sendEvent(
                        ESSArrows.DISABLE_LIST_ITEM_CLICK,
                        this,
                        null
                    )
                } else {
                    EventCenter.sendEvent(ESSArrows.ENABLE_LIST_ITEM_CLICK, this, null)
                }

                textViewAction.text = if (getSelectedImagePath().isEmpty()) {
                    view.context.getString(R.string.camera_cancel)
                }
                else {
                    view.context.getString(R.string.camera_confirm)
                }

                textViewSelected.text = String.format(view.context.getString(R.string.selected_image_count), getSelectedImagePath().size.toString(), maxSelect.toString())
            }

        })

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = albumSelectorAdapter
    }

    fun reload() {
        ThreadUtil.startThread {
            ThreadUtil.startUIThread(0) {
                albumSelectorAdapter.reload(getImageSelectorItemBeanList(0))
            }
        }
    }


    private fun loadMore() {
        ThreadUtil.startThread {
            ThreadUtil.startUIThread(0) {
                albumSelectorAdapter.loadMore(
                    getImageSelectorItemBeanList(
                        albumSelectorAdapter.itemCount,
                    )
                )

            }
        }
    }

    private fun getImageSelectorItemBeanList(offset: Int): MutableList<AlbumSelectorItemBean> {
        val imageSelectorItemBeanList: MutableList<AlbumSelectorItemBean> = mutableListOf()

        for (i in offset until offset + 30) {

            if (i > this@AlbumSelectorViewHolder.albumSelectorItemBeanList.size - 1) {
                return imageSelectorItemBeanList
            }

            imageSelectorItemBeanList.add(this@AlbumSelectorViewHolder.albumSelectorItemBeanList[i])
        }
        return imageSelectorItemBeanList
    }

    fun getSelectedImagePath(): MutableList<String> {
        val list: MutableList<String> = mutableListOf()

        for (i in 0 until selectedList.size) {
            list.add(selectedList[i].filePath)
        }
        return list
    }

}