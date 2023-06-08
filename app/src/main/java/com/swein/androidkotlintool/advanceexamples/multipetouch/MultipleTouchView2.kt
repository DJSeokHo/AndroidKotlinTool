package com.swein.androidkotlintool.advanceexamples.multipetouch

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

/**
 * 多点触摸完整例子(协作型)
 */
class MultipleTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bitmap: Bitmap

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var downX = 0f
    private var downY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = getBitmap(dpToPx(200f), R.drawable.coding_with_cat)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawBitmap(
            bitmap,
            offsetX, offsetY,
            paint
        )
    }

    /**
     * 协作型多点触摸的例子
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {

        // 求所有触控点的中心那个点
        val focusX: Float
        val focusY: Float

        var pointerCount = event.pointerCount

        var sumX = 0f
        var sumY = 0f

        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP

        ILog.debug("??", "外面 $pointerCount")

        for (i in 0 until pointerCount) {
            // 遍历所有点

//            if (!(isPointerUp && i == event.actionIndex)) {
//
//                sumX += event.getX(i)
//                sumY += event.getY(i)
//
//            }

            if (isPointerUp && i == event.actionIndex) {
                // 如果此刻的点是抬起，就不加，否则会出现焦点跳跃
                ILog.debug(">???", "${event.actionMasked} ${event.actionIndex} $pointerCount")
                continue
            }
            else {
                // 如果此刻的点没有抬起，那么就都加上用来计算焦点坐标
                sumX += event.getX(i)
                sumY += event.getY(i)
            }

            ILog.debug("??", "里面 $pointerCount")
        }

        if (isPointerUp) {
            pointerCount--
        }

        focusX = sumX / pointerCount
        focusY = sumY / pointerCount

        /*
        触摸事件是针对view的，不是针对手指，也不是针对设备。

        如果多指按下，分别抬起的话，就会抢夺按下点的控制权，导致图片跳来跳去，怎么办呢？
        getX 还可以追踪，需要存一下id即可
         */

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> {

                downX = focusX
                downY = focusY

                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                // 这里的话，就算多根手指，一根不动，其实每根手指都在实时触发移动，
                // 所以这里是获取不到特定的在移动中的手指(pointer)的id的。

                offsetX = focusX - downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY

                invalidate()
            }
        }

        return true
    }

    private fun getBitmap(targetWidth: Int, resourceId: Int): Bitmap {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resourceId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = targetWidth
        return BitmapFactory.decodeResource(resources, resourceId, options)
    }

    private fun dpToPx(dpValue: Float): Int {
        return (dpValue * resources.displayMetrics.density).toInt()
    }
}