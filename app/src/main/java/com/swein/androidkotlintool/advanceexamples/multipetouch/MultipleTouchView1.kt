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

/**
 * 单点触摸完整例子(包含接力型)
 */
class MultipleTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bitmap: Bitmap

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var downX = 0f
    private var downY = 0f

    private var trackingPointerId = 0

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

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//
//        /*
//        触摸事件是针对view的，不是针对手指，也不是针对设备。
//
//        如果多指按下，分别抬起的话，就会抢夺按下点的控制权，导致图片跳来跳去，怎么办呢？
//        getX 还可以追踪，需要存一下id即可
//         */
//
//        when (event.actionMasked) {
//
//            MotionEvent.ACTION_DOWN -> {
//
//                downX = event.x
//                downY = event.y
//
//                originalOffsetX = offsetX
//                originalOffsetY = offsetY
//            }
//
//            MotionEvent.ACTION_MOVE -> {
//                // 这里的话，就算多根手指，一根不动，其实每根手指都在实时触发移动，
//                // 所以这里是获取不到特定的在移动中的手指(pointer)的id的。
//                offsetX = event.x - downX + originalOffsetX
//                offsetY = event.y - downY + originalOffsetY
//
//                invalidate()
//            }
//
//        }
//
//        return true
//    }

    /**
     * 接力型多点触摸的例子
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {

        /*
        触摸事件是针对view的，不是针对手指，也不是针对设备。

        如果多指按下，分别抬起的话，就会抢夺按下点的控制权，导致图片跳来跳去，怎么办呢？
        getX 还可以追踪，需要存一下id即可
         */

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {

                trackingPointerId = event.getPointerId(0)

                downX = event.x
                downY = event.y

                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_POINTER_DOWN -> {

                val actionIndex = event.actionIndex

                trackingPointerId = event.getPointerId(actionIndex) // 多点触控时，可以拿到当前活动的pointer的Id

                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)

                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                // 这里的话，就算多根手指，一根不动，其实每根手指都在实时触发移动，
                // 所以这里是获取不到特定的在移动中的手指(pointer)的id的。

                val index = event.findPointerIndex(trackingPointerId)

                offsetX = event.getX(index) - downX + originalOffsetX
                offsetY = event.getY(index) - downY + originalOffsetY

                invalidate()
            }

            MotionEvent.ACTION_POINTER_UP -> {

                // 拿到正要放开的手指的id
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)

                // 看看是不是正在跟踪的手指，是的话就要找个新的手指接棒
                if (pointerId == trackingPointerId) {

                    // 选择最后一根手指来接棒
                    val newIndex = if (actionIndex == event.pointerCount - 1) {
                        // 这里，比如我有 a,b,c 三个手指
                        // 离开一个c，要找b接棒，如果 actionIndex在这里是event.pointerCount - 1的话，也不对
                        // 因为此时触发 ACTION_POINTER_UP时，pointerCount还是3，剪掉1的话，那么index是2，
                        // index是从0开始酸的，如果是2的话，那么等于还是取的c，但是c不存在了啊，所以这里要剪掉2
                        event.pointerCount - 2
                    }
                    else {
                        event.pointerCount - 1
                    }

                    trackingPointerId = event.getPointerId(newIndex) // 多点触控时，可以拿到当前活动的pointer的Id

                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)

                    originalOffsetX = offsetX
                    originalOffsetY = offsetY

                }
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