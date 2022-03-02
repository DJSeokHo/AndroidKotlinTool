package com.swein.androidkotlintool.main.examples.patternlockexample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlin.math.abs

class PatternView : View {

    companion object {
        private const val TAG = "PatternView"
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var density: Float = context.resources.displayMetrics.density
    private val paint = Paint()

    private val pointPositions = mutableListOf<PatternPoint>()
    private val patternPath = mutableListOf<PatternPoint>()

    var onCheckPattern: ((patternString: String) -> Unit)? = null
    var onPatternChanged: ((patternString: String) -> Unit)? = null
    var onVibrate: (() -> Unit)? = null

    private var touchedX = 0.0f
    private var touchedY = 0.0f

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = measuredWidth
        var height = measuredHeight

        width = resolveSize(width, widthMeasureSpec)
        height = resolveSize(height, heightMeasureSpec)

        setMeasuredDimension(width, height)

        initPatternPoints(width * 0.5f, height * 0.5f, 8 * density)
    }

    /**
     * 顺序绘制，后面的会覆盖前面的
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.isAntiAlias = true

        drawBackground(canvas)
        drawCircles(canvas)
        drawPatternPath(canvas)
        drawTempPattern(canvas)
    }

    private fun initPatternPoints(centerX: Float, centerY: Float, radius: Float) {

        pointPositions.clear()
        patternPath.clear()

        // row 1
        var point = PatternPoint(centerX * 0.5f, centerY * 0.5f, radius, "a") // 11
        pointPositions.add(point)

        point = PatternPoint(centerX, centerY * 0.5f, radius, "b") // 12
        pointPositions.add(point)

        point = PatternPoint(centerX * 0.5f + centerX, centerY * 0.5f, radius, "c") // 13
        pointPositions.add(point)

        // row 2
        point = PatternPoint(centerX * 0.5f, centerY, radius, "d") // 21
        pointPositions.add(point)

        point = PatternPoint(centerX, centerY, radius, "e") // 22
        pointPositions.add(point)

        point = PatternPoint(centerX * 0.5f + centerX, centerY, radius, "f") // 23
        pointPositions.add(point)

        // row 3
        point = PatternPoint(centerX * 0.5f, centerY * 0.5f + centerY, radius, "g") // 31
        pointPositions.add(point)

        point = PatternPoint(centerX, centerY * 0.5f + centerY, radius, "h") // 32
        pointPositions.add(point)

        point = PatternPoint(centerX * 0.5f + centerX, centerY * 0.5f + centerY, radius, "i") // 33
        pointPositions.add(point)
    }

    private fun onPatternChangedListener() {

        onPatternChanged?.let { onPatternChanged ->

            var patternString = ""

            for (point in patternPath) {
                if (point.isSelected) {
                    patternString += point.pattern
                }
            }

            ILog.debug(TAG, "pattern is $patternString")
            onPatternChanged(patternString)
        }
    }

    private fun checkPatternString() {

        onCheckPattern?.let { onCheckPattern ->

            var patternString = ""

            for (point in patternPath) {
                if (point.isSelected) {
                    patternString += point.pattern
                }
            }

            ILog.debug(TAG, "pattern is $patternString")
            onCheckPattern(patternString)
        }
    }

    private fun clearPattern() {
        for (point in pointPositions) {
            point.isSelected = false
        }

        touchedX = 0f
        touchedY = 0f

        patternPath.clear()
    }

    private fun drawBackground(canvas: Canvas?) {
        canvas?.drawColor(Color.parseColor("#666666"))
    }

    private fun drawCircles(canvas: Canvas?) {

        paint.color = Color.parseColor("#ffffff")
        paint.strokeWidth = 8.0f

        for (point in pointPositions) {
            paint.style = if (point.isSelected) {
                Paint.Style.FILL
            }
            else {
                Paint.Style.STROKE
            }
            canvas?.drawCircle(point.x, point.y, point.r, paint) // 1
        }
    }

    private fun drawPatternPath(canvas: Canvas?) {

        paint.color = Color.WHITE
        paint.strokeWidth = 20f

        if (patternPath.size < 2) {
            return
        }

        if (patternPath.size > pointPositions.size) {
            return
        }

        for (i in 1 until patternPath.size) {
            canvas?.drawLine(patternPath[i - 1].x, patternPath[i - 1].y, patternPath[i].x, patternPath[i].y, paint)
        }
    }

    private fun drawTempPattern(canvas: Canvas?) {

        paint.color = Color.BLACK
        paint.strokeWidth = 20f

        if (patternPath.size == 0) {
            return
        }

        if (patternPath.size >= pointPositions.size) {
            return
        }

        canvas?.drawLine(
            patternPath[patternPath.size - 1].x,
            patternPath[patternPath.size - 1].y,
            touchedX, touchedY, paint
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                ILog.debug(TAG, "ACTION_UP ${event.x} ${event.y}")
            }

            MotionEvent.ACTION_MOVE -> {

                touchedX = event.x
                touchedY = event.y

                for (point in pointPositions) {
                    if (
                        abs(event.x - point.x) < point.r * 1.5
                        && abs(event.y - point.y) < point.r * 1.5
                    ) {

                        if (!point.isSelected) {
                            point.isSelected = true
                            patternPath.add(point)

                            onVibrate?.let { onVibrate ->
                                onVibrate()
                            }

                            onPatternChangedListener()
                        }
                    }
                }

                invalidate()
            }

            MotionEvent.ACTION_UP -> {

                ILog.debug(TAG, "ACTION_UP ${event.x} ${event.y}")

                checkPatternString()

                clearPattern()
                invalidate()
            }

            else -> Unit
        }

        return true
    }

    data class PatternPoint(
        val x: Float = 0.0f,
        val y: Float = 0.0f,
        val r: Float = 0.0f,
        val pattern: String = ""
    ) {
        var isSelected = false
    }
}