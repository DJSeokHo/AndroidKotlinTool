package com.swein.androidkotlintool.main.examples.inpersonsigning

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.max
import kotlin.math.min


class SignatureView: View {

    private var strokeWidth = 10f
    private var strokeColor = Color.BLACK
    private var background = Color.WHITE

    private val paint: Paint = Paint()
    private val path: Path = Path()

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private val rect = RectF()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    private fun init() {
        paint.isAntiAlias = true
        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = strokeWidth
    }

    fun setStrokeWidth(strokeWidth: Float) {
        paint.strokeWidth = strokeWidth
    }

    fun setStrokeColor(strokeColor: Int) {
        paint.color = strokeColor
    }

    fun setBackground(background: Int) {
        this.background = background
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                lastTouchX = event.x
                lastTouchY = event.y

                return true
            }

            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {

                resetRect(event.x, event.y)

                val historySize = event.historySize

                for (i in 0 until historySize) {

                    val historicalX = event.getHistoricalX(i)
                    val historicalY = event.getHistoricalY(i)

                    expandRect(historicalX, historicalY)

                    path.lineTo(historicalX, historicalY)

                }

                path.lineTo(event.x, event.y)

            }

            else -> {
                return false
            }
        }

        invalidate()

        lastTouchX = event.x
        lastTouchY = event.y

        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(background)
        canvas.drawPath(path, paint)
    }

    private fun expandRect(historicalX: Float, historicalY: Float) {
        if (historicalX < rect.left) {
            rect.left = historicalX
        }
        else if (historicalX > rect.right) {
            rect.right = historicalX
        }
        if (historicalY < rect.top) {
            rect.top = historicalY
        }
        else if (historicalY > rect.bottom) {
            rect.bottom = historicalY
        }
    }

    private fun resetRect(eventX: Float, eventY: Float) {
        rect.left = min(lastTouchX, eventX)
        rect.right = max(lastTouchX, eventX)
        rect.top = min(lastTouchY, eventY)
        rect.bottom = max(lastTouchY, eventY)
    }

    fun clear() {
        path.reset()
        // 重新绘制整个视图
        invalidate()
    }

//    fun getBitmapFromView(): Bitmap {
//        this.isDrawingCacheEnabled = true  //开启图片缓存
//        buildDrawingCache()  //构建图片缓存
//        val bitmap = Bitmap.createBitmap(drawingCache)
//        isDrawingCacheEnabled = false  //关闭图片缓存
//        return bitmap
//    }
}