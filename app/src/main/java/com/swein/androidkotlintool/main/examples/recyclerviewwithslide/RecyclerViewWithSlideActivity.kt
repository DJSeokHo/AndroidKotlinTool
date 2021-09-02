package com.swein.androidkotlintool.main.examples.recyclerviewwithslide

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.display.DisplayUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.recyclerviewwithslide.adapter.SlideAdapter
import java.lang.ref.WeakReference
import kotlin.math.max

class RecyclerViewWithSlideActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val adapter = SlideAdapter()
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_with_slide)


        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false

            val list = mutableListOf<Int>()
            for (i in 0 until 50) {
                list.add(i)
            }

            adapter.reload(list)
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val list = mutableListOf<Int>()
        for (i in 0 until 50) {
            list.add(i)
        }

        adapter.reload(list)

        setItemTouchHelper()
    }

    private fun setItemTouchHelper() {

        ItemTouchHelper(object : ItemTouchHelper.Callback() {

//            private val defaultScrollX = DisplayUtil.dipToPx(this@RecyclerViewWithSlideActivity, 100f)
//            private var currentScrollX = 0
//            private var currentScrollXWhenInactive = 0
//            private var initXWhenInactive = 0f
//            private var firstInactive = false

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {

                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.LEFT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                ILog.debug("???", "onSwiped $direction")
                if (direction == 4) {
                    ILog.debug("???", "should delete")
                    adapter.removeItem(viewHolder)
                }
            }

//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//
//                /*
//                    if screen of phone is 1080px
//                    then left offset limit is -1080
//                    right offset limit is 1080
//                 */
////                ILog.debug("???", "${viewHolder.itemView.scrollX} $dX $actionState $isCurrentlyActive")
//
////                viewHolder.itemView.scrollTo(currentScrollX - dX.toInt(), 0)
//
////                var scrollX = -dX.toInt()
////                ILog.debug("???", "$dX")
//////                    if (currentScrollX > defaultScrollX) {
//////                        currentScrollX = defaultScrollX
//////                    }
//////                    else if (currentScrollX < 0) {
//////                        currentScrollX = 0
//////                    }
////                viewHolder.itemView.scrollTo(scrollX, 0)
//
//
////                if (dX == 0f) {
////
////                    currentScrollX = viewHolder.itemView.scrollX
////                    firstInactive = true
////
////                }
////
////                if (isCurrentlyActive) {
////
////                    if (viewHolder.itemView.scrollX <= 0) {
////                        return
////                    }
////
////                    viewHolder.itemView.scrollTo(currentScrollX + (-dX).toInt(), 0)
////                }
////                else {
////
////                }
////                // 首次滑动时，记录下ItemView当前滑动的距离
////                if (dX == 0f) {
////                    mCurrentScrollX = viewHolder.itemView.scrollX
////                    mFirstInactive = true
////                }
////                if (isCurrentlyActive) { // 手指滑动
////                    // 基于当前的距离滑动
////                    viewHolder.itemView.scrollTo(mCurrentScrollX + (-dX).toInt(), 0)
////                } else { // 动画滑动
////                    if (mFirstInactive) {
////                        mFirstInactive = false
////                        mCurrentScrollXWhenInactive = viewHolder.itemView.scrollX
////                        mInitXWhenInactive = dX
////                    }
////                    if (viewHolder.itemView.scrollX >= mDefaultScrollX) {
////                        // 当手指松开时，ItemView的滑动距离大于给定阈值，那么最终就停留在阈值，显示删除按钮。
////                        viewHolder.itemView.scrollTo(max(mCurrentScrollX + (-dX).toInt(), mDefaultScrollX), 0)
////                    } else {
////                        // 这里只能做距离的比例缩放，因为回到最初位置必须得从当前位置开始，dx不一定与ItemView的滑动距离相等
////                        viewHolder.itemView.scrollTo((mCurrentScrollXWhenInactive * dX / mInitXWhenInactive).toInt(), 0)
////                    }
////                }
//            }

        }).apply {
            attachToRecyclerView(recyclerView)
        }

    }
}
