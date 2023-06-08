package com.swein.androidkotlintool.advanceexamples.scalableimageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

private const val EXTRA_SCALE_FRACTION = 1.5f

class ScalableImageViewForExample(
    context: Context, attrs: AttributeSet
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bitmap: Bitmap

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var smallScale = 0f
    private var bigScale = 0f

    private var scale = 0f

    private var zoomIn = false

    private var scroller = OverScroller(context)

    private val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            ILog.debug("??", "onScale ${scaleGestureDetector.scaleFactor}")

            return true
        }

        override fun onScaleBegin(p0: ScaleGestureDetector): Boolean {
            ILog.debug("??", "onScaleBegin")
            return false
        }

        override fun onScaleEnd(p0: ScaleGestureDetector) {
            ILog.debug("??", "onScaleEnd")
        }
    })

    private val gestureDetectorCompat = GestureDetectorCompat(context, object : SimpleOnGestureListener() {

            override fun onDown(e: MotionEvent): Boolean {
                // 这个是拦截，必须写
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {

                zoomIn = !zoomIn

                if (!zoomIn) {

                    // reset
                    offsetX = 0f
                    offsetY = 0f
                }
                else {
                    // 点击的位置为中心进行放大处理，而不是以屏幕中心为放大中心，需要修正。
                    offsetX = (e.x - width * 0.5f) * (1 - bigScale / smallScale)
                    offsetY = (e.y - height * 0.5f) * (1 - bigScale / smallScale)

                    fixBoundary()
                }

                invalidate()

                return false
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                ILog.debug("???", "onScroll will call when finger move on the screen")

                if (!zoomIn) {
                    // 缩小状态不能移动
                    return false
                }

                offsetX -= distanceX
                offsetY -= distanceY

                fixBoundary()

                invalidate()

                return false
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (!zoomIn) {
                    return false
                }

                // fling scroll
                scroller.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) * 0.5f).toInt(),
                    ((bitmap.width * bigScale - width) * 0.5f).toInt(),
                    (-(bitmap.height * bigScale - height) * 0.5f).toInt(),
                    ((bitmap.height * bigScale - height) * 0.5f).toInt(),
                    dpToPx(this@ScalableImageViewForExample.context, 40f),
                    dpToPx(this@ScalableImageViewForExample.context, 40f)
                )

                postOnAnimation {
//            post {
                    flingScroll()
                }

                return false
            }

            private fun flingScroll() {
                if (scroller.computeScrollOffset()) {

                    offsetX = scroller.currX.toFloat()
                    offsetY = scroller.currY.toFloat()

                    invalidate()

                    postOnAnimation {
//                post {
                        flingScroll()
                    }
                }
            }
        }
    ).apply {
        setIsLongpressEnabled(false)
    }

    fun zoomIn(toggle: Boolean) {
        zoomIn = toggle

        if (!zoomIn) {

            // reset
            offsetX = 0f
            offsetY = 0f
        }

        invalidate()
    }

    private fun fixBoundary() {

        val boundaryWidth = (bitmap.width * bigScale - width) * 0.5f

        if (offsetX > boundaryWidth) {
            offsetX = boundaryWidth
        }

        if (offsetX < -boundaryWidth) {
            offsetX = -boundaryWidth
        }

        val boundaryHeight = (bitmap.height * bigScale - height) * 0.5f

        if (offsetY > boundaryHeight) {
            offsetY = boundaryHeight
        }

        if (offsetY < -boundaryHeight) {
            offsetY = -boundaryHeight
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetectorCompat.onTouchEvent(event)
//        return scaleGestureDetector.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = getBitmap(width, R.drawable.wide_image)

        originalOffsetX = (width - bitmap.width) * 0.5f
        originalOffsetY = (height - bitmap.height) * 0.5f


        if (bitmap.width.toFloat() / bitmap.height.toFloat() > width.toFloat() / height.toFloat()) {

            /*
            if the image is a wide image or rect image
            in normal mode, the left and right side of the image should match to the left and right side of the screen
            in big mode, the top and bottom side of the image should match to the top and bottom side of the screen
            in zoom in mode, the image should be in big mode and multiple the extra scale, in this example, extra scale is 1.5.
             */

            smallScale = width.toFloat() / bitmap.width.toFloat()
            bigScale = height.toFloat() / bitmap.height.toFloat() * EXTRA_SCALE_FRACTION
        }
        else {

            /*
            if the image is a long image
            in normal mode, the top and bottom side of the image should match to the top and bottom side of the screen
            in big mode, the left and right side of the image should match to the left and right side of the screen
            in zoom in mode, the image should be in big mode and multiple the extra scale, in this example, extra scale is 1.5.
             */

            smallScale = height.toFloat() / bitmap.height.toFloat()
            bigScale = width.toFloat() / bitmap.width.toFloat() * EXTRA_SCALE_FRACTION
        }

        scale = smallScale

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 放大之后坐标系也会跟着放大，会发生图的移动比手指快的情况，所以放大前，先随着手移动就可以解决
        canvas?.translate(offsetX, offsetY)

        val scale = if (zoomIn) {
            bigScale
        }
        else {
            smallScale
        }

        canvas?.scale(scale, scale, width.toFloat() * 0.5f, height.toFloat() * 0.5f)
        canvas?.drawBitmap(
            bitmap,
            originalOffsetX, originalOffsetY,
            paint
        )
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

    private fun dpToPx(context: Context, dpValue: Float): Int {
        return (dpValue * context.resources.displayMetrics.density).toInt()
    }


//    private var gestureDetectorCompat = GestureDetectorCompat(context, object : GestureDetector.OnGestureListener {
//        override fun onDown(p0: MotionEvent): Boolean {
//            // intercept touch events of the view
//            return true
//        }
//
//        override fun onShowPress(p0: MotionEvent) {
//            ILog.debug("???", "onShowPress")
//        }
//
//        override fun onSingleTapUp(p0: MotionEvent): Boolean {
//            ILog.debug("???", "onSingleTapUp")
//            return false
//        }
//
////        override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
//        override fun onScroll(p0: MotionEvent, p1: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
//            ILog.debug("???", "onScroll will call when finger move on the screen")
//
//            if (!zoomIn) {
//                // 缩小状态不能移动
//                return false
//            }
//
//            offsetX -= distanceX
//            offsetY -= distanceY
//
//            fixBoundary()
//
//            invalidate()
//
//            return false
//        }
//
//        override fun onLongPress(p0: MotionEvent) {
//            ILog.debug("???", "onLongPress")
//        }
//
//        private fun flingScroll() {
//            if (scroller.computeScrollOffset()) {
//
//                offsetX = scroller.currX.toFloat()
//                offsetY = scroller.currY.toFloat()
//
//                invalidate()
//
//                postOnAnimation {
////                post {
//                    flingScroll()
//                }
//            }
//        }
//
////        override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
//        override fun onFling(p0: MotionEvent, p1: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//
//            if (!zoomIn) {
//                return false
//            }
//
//            // fling scroll
//            scroller.fling(
//                offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
//                (-(bitmap.width * bigScale - width) * 0.5f).toInt(),
//                ((bitmap.width * bigScale - width) * 0.5f).toInt(),
//                (-(bitmap.height * bigScale - height) * 0.5f).toInt(),
//                ((bitmap.height * bigScale - height) * 0.5f).toInt(),
//                dpToPx(this@ScalableImageView.context, 40f),
//                dpToPx(this@ScalableImageView.context, 40f)
//            )
//
//            postOnAnimation {
////            post {
//                flingScroll()
//            }
//
//            return false
//        }
//
//    }).apply {
//        setIsLongpressEnabled(false)
//        setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
//            override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
//                return false
//            }
//
//            override fun onDoubleTap(event: MotionEvent): Boolean {
//                zoomIn = !zoomIn
//
//                if (!zoomIn) {
//
//                    // reset
//                    offsetX = 0f
//                    offsetY = 0f
//                }
//                else {
//                    // 点击的位置为中心进行放大处理，而不是以屏幕中心为放大中心，需要修正。
//                    offsetX = (event.x - width * 0.5f) * (1 - bigScale / smallScale)
//                    offsetY = (event.y - height * 0.5f) * (1 - bigScale / smallScale)
//
//                    fixBoundary()
//                }
//
//                invalidate()
//
//                return false
//            }
//
//            override fun onDoubleTapEvent(p0: MotionEvent): Boolean {
//                return false
//            }
//        })
//    }

}
