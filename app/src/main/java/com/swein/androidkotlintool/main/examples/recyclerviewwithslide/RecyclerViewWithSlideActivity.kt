package com.swein.androidkotlintool.main.examples.recyclerviewwithslide

import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.display.DisplayUtility
import com.swein.androidkotlintool.main.examples.recyclerviewwithslide.adapter.SlideAdapter

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

            private val limitScrollX = DisplayUtility.dipToPx(this@RecyclerViewWithSlideActivity, 100f)
            private var currentScrollX = 0
            private var currentScrollXWhenInactive = 0
            private var initXWhenInactive = 0f
            private var firstInactive = false

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {

                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    // 首次滑动时，记录下ItemView当前滑动的距离
                    if (dX == 0f) {
                        currentScrollX = viewHolder.itemView.scrollX
                        firstInactive = true
                    }

                    if (isCurrentlyActive) { // 手指滑动
                        // 基于当前的距离滑动

                        var scrollOffset = currentScrollX + (-dX).toInt()
                        if (scrollOffset > limitScrollX) {
                            scrollOffset = limitScrollX
                        }
                        else if (scrollOffset < 0) {
                            scrollOffset = 0
                        }

                        viewHolder.itemView.scrollTo(scrollOffset, 0)
                    }
                    else {
                        // 动画滑动
                        if (firstInactive) {
                            firstInactive = false
                            currentScrollXWhenInactive = viewHolder.itemView.scrollX
                            initXWhenInactive = dX
                        }
//                    if (viewHolder.itemView.scrollX >= defaultScrollX) {
//                        // 当手指松开时，ItemView的滑动距离大于给定阈值，那么最终就停留在阈值，显示删除按钮。
//                        viewHolder.itemView.scrollTo((currentScrollX + (-dX).toInt()).coerceAtLeast(defaultScrollX), 0)
//                    } else {
//                        // 这里只能做距离的比例缩放，因为回到最初位置必须得从当前位置开始，dx不一定与ItemView的滑动距离相等
//                        viewHolder.itemView.scrollTo((currentScrollXWhenInactive * dX / initXWhenInactive).toInt(), 0)
//                    }

                        if (viewHolder.itemView.scrollX < limitScrollX) {
                            // 当手指松开时，ItemView的滑动距离大于给定阈值，那么最终就停留在阈值，显示删除按钮。
                            viewHolder.itemView.scrollTo((currentScrollXWhenInactive * dX / initXWhenInactive).toInt(), 0)
                        }
                    }

                }

            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)

                if (viewHolder.itemView.scrollX > limitScrollX) {
                    viewHolder.itemView.scrollTo(limitScrollX, 0)
                }
                else if (viewHolder.itemView.scrollX < 0) {
                    viewHolder.itemView.scrollTo(0, 0)
                }
            }

        }).apply {
            attachToRecyclerView(recyclerView)
        }

    }
}
