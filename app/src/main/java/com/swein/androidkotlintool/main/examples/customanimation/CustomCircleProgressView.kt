package com.swein.androidkotlintool.main.examples.customanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomCircleProgressView: View {

    var progress = 0.0f
    private val paint = Paint()

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }

    // 必须提供一个getter给ObjectAnimator
    fun getProgressValue(): Float {
        return progress
    }

    // 提供一个setter给ObjectAnimator
    fun setProgressValue(progress: Float) {
        this.progress = progress
        //请求立即重绘
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = Color.RED
        paint.strokeWidth = 3f

        paint.style = Paint.Style.STROKE // 画线模式
        canvas?.drawArc(0f, 0f, 300f, 300f, 135f, progress * 2.7f, false, paint) // 绘制不封口的弧形
    }
}