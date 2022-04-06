package com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.display.DisplayUtility
import com.swein.androidkotlintool.main.examples.rotatingcirclemenu.circlemenu.model.CircleMenuModel
import kotlin.math.*

class CircleMenuLayout: ViewGroup {

    companion object {
        private const val TAG = "CircleMenuLayout"

        private const val DISABLE_CLICK_VALUE = 3

        // 150 is a best value I think...
        private const val AUTO_ROTATING_THRESHOLD_VALUE = 150
    }

    private var circleMenuRadius = 0
    private var circleMenuItemStartAngle = -90f

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)


    fun initView(backgroundResource: Int, widthDP: Float) {
        setBackgroundResource(backgroundResource)
        circleMenuRadius = (DisplayUtility.dipToPx(context, widthDP) * 0.5f).toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        /*
        不限制(UNSPECIFIED) : UNSPECIFIED = 0
        还是限制个最大值(AT_MOST)，让子view不超过这个值 : AT_MOST = -2147483648
        还是直接限制死，我让你是多少就得是多少(EXACTLY) : EXACTLY = 1073741824
         */
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
//        ILog.debug(TAG, "with mode is $widthMode, height mode is $heightMode")

        val viewGroupWidth = MeasureSpec.getSize(widthMeasureSpec)
        val viewGroupHeight = MeasureSpec.getSize(heightMeasureSpec)

        for (i in 0 until childCount) {

            val childView = getChildAt(i)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
        }

        setMeasuredDimension(viewGroupWidth, viewGroupHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

//        for (i in 0 until childCount) {
//
//            val childView = getChildAt(i) as CircleMenuItemView
//
//            val childWidth = childView.measuredWidth
//            val childHeight = childView.measuredHeight
//
//            val addEnableColumnIndex = findAddEnableColumnIndex(childView)
//            ILog.debug("???", "can add to column $addEnableColumnIndex")
//            childView.column = addEnableColumnIndex
//
//            val childTop = childView.positionY
//            val childBottom = childTop + childHeight
//            val childLeft = childWidth * addEnableColumnIndex
//            val childRight = childLeft + childWidth
//
//            childView.layout(childLeft, childTop, childRight, childBottom)
//        }

        if (childCount == 0) {
            return
        }

        // calculate angle
//        ILog.debug(TAG, "childCount is $childCount")
        val angleDelay = 360 / childCount

        // calculate item position
        for (i in 0 until childCount) {

            val circleMenuItemView = getChildAt(i)
            val childWidth = circleMenuItemView.measuredWidth
            val childHeight = circleMenuItemView.measuredHeight

            /*
            calculate the distance, use the distance as the radius between circle menu center and item center
             */
            val distanceBetweenMenuCenterAndItemCenter = circleMenuRadius - childWidth * 0.5 - 20 // 10 is padding

//            ILog.debug(TAG, "circleMenuRadius $circleMenuRadius, " +
//                    "childWidth $childWidth, childHeight $childHeight, " +
//                    "distanceBetweenMenuCenterAndItemCenter $distanceBetweenMenuCenterAndItemCenter"
//            )

            circleMenuItemStartAngle %= 360f
//            ILog.debug(TAG, "?????? $circleMenuItemStartAngle")

            // cosa: center X of menu item
            val leftEdge = circleMenuRadius + round(
                distanceBetweenMenuCenterAndItemCenter * cos(Math.toRadians(circleMenuItemStartAngle.toDouble())) - 0.5 * childWidth
            ).toInt()

            // sina: center Y of menu item
            val topEdge= circleMenuRadius + round(
                distanceBetweenMenuCenterAndItemCenter * sin(Math.toRadians(circleMenuItemStartAngle.toDouble())) - 0.5 * childWidth
            ).toInt()

            circleMenuItemView.layout(leftEdge, topEdge, leftEdge + childWidth, topEdge + childWidth)

            // add angle
            circleMenuItemStartAngle += angleDelay
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
    }

    private var touchDownX = 0f
    private var touchDownY = 0f

    private var tempAngleBetweenTouchDownAndTouchUp = 0f
    private var totalTimeBetweenTouchDownAndTouchUp = 0L

    private var isAutoRotating = false

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        val x = event.x
        val y = event.y

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
//                ILog.debug(TAG, "ACTION_DOWN $x $y, center is $circleMenuRadius, $circleMenuRadius")

                touchDownX = x
                touchDownY = y

                totalTimeBetweenTouchDownAndTouchUp = System.currentTimeMillis()

                tempAngleBetweenTouchDownAndTouchUp = 0f

                if (isAutoRotating) {
                    // 移除快速滚动的回调
                    removeCallbacks(autoRotatingRunnable)
                    isAutoRotating = false
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {

                val touchDownAngle = calculateTouchPositionAngle(touchDownX, touchDownY, circleMenuRadius.toFloat())
                val movedAngle = calculateTouchPositionAngle(x, y, circleMenuRadius.toFloat())

                val quadrant = calculateQuadrant(x, y, circleMenuRadius.toFloat())

                if (quadrant == 1 || quadrant == 4) {
                    circleMenuItemStartAngle += (movedAngle - touchDownAngle)
                    tempAngleBetweenTouchDownAndTouchUp += movedAngle - touchDownAngle
                }
                else
                {
                    circleMenuItemStartAngle += (touchDownAngle - movedAngle)
                    tempAngleBetweenTouchDownAndTouchUp += touchDownAngle - movedAngle
                }

                requestLayout()

                touchDownX = x
                touchDownY = y
            }
            MotionEvent.ACTION_UP -> {

                ILog.debug(TAG, "ACTION_UP tempAngleBetweenTouchDownAndTouchUp: $tempAngleBetweenTouchDownAndTouchUp")

                val anglePerSecond = (tempAngleBetweenTouchDownAndTouchUp * 1000
                        / (System.currentTimeMillis() - totalTimeBetweenTouchDownAndTouchUp))

                ILog.debug(TAG, "anglePerSecond $anglePerSecond")

                // if rotating velocity per second big then AUTO_ROTATING_THRESHOLD_VALUE, should auto rotating even after ACTION_UP
                if (abs(anglePerSecond) > AUTO_ROTATING_THRESHOLD_VALUE && !isAutoRotating) {

                    post(AutoRotatingRunnable(anglePerSecond, shouldStopAutoRotating = {
                        isAutoRotating = false
                    }, onRePost = {
                        postDelayed(it, 16)
                    }).also {
                        autoRotatingRunnable = it
                    })

                    return true
                }

                if (abs(tempAngleBetweenTouchDownAndTouchUp) > DISABLE_CLICK_VALUE) {
                    // if rotated ange is big then DISABLE_CLICK_VALUE, disable item click
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * 自动滚动的Runnable
     */
    private var autoRotatingRunnable: AutoRotatingRunnable? = null

    private inner class AutoRotatingRunnable(
        private var angelPerSecond: Float,
        private val shouldStopAutoRotating: () -> Unit,
        private val onRePost: (runnable: Runnable) -> Unit
        ) : Runnable {

        override fun run() {

            if (abs(angelPerSecond).toInt() < 20) {
                shouldStopAutoRotating()
                return
            }

            isAutoRotating = true
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            circleMenuItemStartAngle += angelPerSecond / 20
            // 逐渐减小这个值
            angelPerSecond *= 0.98f

            requestLayout()

            onRePost(this)

        }
    }

    private fun calculateTouchPositionAngle(x: Float, y: Float, radius: Float): Float {
        val tempX = x - radius
        val tempY = y - radius
        return (asin(tempY / hypot(tempX, tempY)) * 180 / Math.PI).toFloat()
    }

    private fun calculateQuadrant(x: Float, y: Float, radius: Float): Int {
        // radius as the center point of the circle menu

        return when {
            x >= radius && y >= radius -> {
                4
            }

            x >= radius && y < radius -> {
                1
            }

            x < radius && y >= radius -> {
                3
            }

            x < radius && y < radius -> {
                2
            }
            else -> {
                0
            }
        }

    }
}