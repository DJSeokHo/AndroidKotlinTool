package com.swein.androidkotlintool.advanceexamples.customtouchevent

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)

        // actionMasked：获取事件，用于单点和多点
        if (event.actionMasked == MotionEvent.ACTION_UP) {
            // 触发点击
            performClick()
        }

//        // 单点
//        event.action
//        MotionEvent.ACTION_UP
//        MotionEvent.ACTION_DOWN
//        MotionEvent.ACTION_CANCEL
//        MotionEvent.ACTION_MOVE
//
//        // 多点
//        event.actionMasked
//        MotionEvent.ACTION_POINTER_DOWN
//        MotionEvent.ACTION_POINTER_UP

        return true // 这里代表这个view将要消费一组点击事件，也就是拦截掉，让事件不再向下穿透
//        return event.actionMasked == MotionEvent.ACTION_DOWN // 如果和DOWN事件绑定，就算其他事件
        // 比如 UP, MOVE 时返回false也没有意义，只要DOWN绑定了，那么就和直接 return true一样，全部拦截掉，不再向下穿透
    }
}