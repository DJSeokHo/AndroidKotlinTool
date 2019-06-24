package com.swein.androidkotlintool.template.customview

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs

class SlidingAbleSwipeRefreshLayout: SwipeRefreshLayout {

    private var startX: Float? = null
    private var startY: Float? = null

    private var isViewDragged: Boolean = false
    private var touchSlop: Int

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        when(ev?.action) {

            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y

                isViewDragged = false
            }

            MotionEvent.ACTION_MOVE -> {

                if(isViewDragged) {
                    return false
                }

                val endX = ev.x
                val endY = ev.y

                val distanceX = abs(endX - startX!!)
                val distanceY = abs(endY - startY!!)

                if(distanceX > touchSlop && distanceX > distanceY) {
                    isViewDragged = true
                    return false
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isViewDragged = false
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

}