package com.swein.androidkotlintool.framework.module.shcameraphoto.album.selector.adapter.item

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.album.albumselectorwrapper.bean.AlbumSelectorItemBean
import com.swein.androidkotlintool.framework.util.bitmap.BitmapUtil
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.framework.util.glide.SHGlide
import java.lang.ref.WeakReference

class AlbumSelectorItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val TAG = "AlbumSelectorItemViewHolder"
    }

    interface AlbumSelectorItemViewHolderDelegate {
        fun onSelected()
    }

    lateinit var albumSelectorItemBean: AlbumSelectorItemBean

    private var view: WeakReference<View> = WeakReference(itemView)

    private lateinit var imageView: ImageView
    private lateinit var imageViewCheck: ImageView

    private var bitmap: Bitmap? = null

    var albumSelectorItemViewHolderDelegate: AlbumSelectorItemViewHolderDelegate? = null

    private var click = true

    init {

        findView()
        setListener()
        initView()
    }

    private fun initESS() {
        EventCenter.addEventObserver(
            ESSArrows.ENABLE_LIST_ITEM_CLICK, this, object : EventCenter.EventRunnable {
                override fun run(arrow: String, poster: Any, data: MutableMap<String, Any>?) {
                    click = true
                }
            })

        EventCenter.addEventObserver(
            ESSArrows.DISABLE_LIST_ITEM_CLICK, this, object : EventCenter.EventRunnable {
                override fun run(arrow: String, poster: Any, data: MutableMap<String, Any>?) {
                    click = false
                }
            })
    }

    private fun findView() {

        view.get()?.let {
            imageView = it.findViewById(R.id.imageView)
            imageViewCheck = it.findViewById(R.id.imageViewCheck)
        }
    }

    private fun setListener() {

        imageViewCheck.setOnClickListener {
            if (!albumSelectorItemBean.isSelected && !click) {
                return@setOnClickListener
            }
            albumSelectorItemBean.isSelected = !albumSelectorItemBean.isSelected
            toggleCheck()
        }

        imageView.setOnClickListener {
            if (!albumSelectorItemBean.isSelected && !click) {
                return@setOnClickListener
            }
            albumSelectorItemBean.isSelected = !albumSelectorItemBean.isSelected
            toggleCheck()
        }
    }

    private fun initView() {
        imageView.post {
            val layoutParams = imageView.layoutParams
            layoutParams.height = imageView.width
        }
    }

    fun updateView() {

        view.get()?.let {
            imageView.setImageBitmap(null)
            initESS()
            toggleCheck()
            ThreadUtil.startThread {

                val degree: Int = BitmapUtil.readPictureDegree(albumSelectorItemBean.filePath)
                bitmap = BitmapUtil.rotateImageWithoutStore(albumSelectorItemBean.filePath, degree, 1f)

                ThreadUtil.startUIThread(0) {
                    try {
                        SHGlide.setImageBitmapNoAnimation(
                            it.context, bitmap, imageView, null,
                            imageView.width, imageView.height, 0.7f, 0f
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

    }

    private fun toggleCheck() {
        albumSelectorItemViewHolderDelegate!!.onSelected()
        if (albumSelectorItemBean.isSelected) {
            imageViewCheck.setImageResource(R.drawable.ti_check)
        } else {
            imageViewCheck.setImageResource(R.drawable.ti_un_check)
        }
    }

    private fun removeESS() {
        EventCenter.removeAllObserver(this)
    }

    fun clear() {
        imageView.setImageBitmap(null)
    }


    protected fun finalize() {
        removeESS()
    }

}