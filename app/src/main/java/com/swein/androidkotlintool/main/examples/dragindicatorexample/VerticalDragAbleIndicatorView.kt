package com.swein.androidkotlintool.main.examples.dragindicatorexample

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class VerticalDragAbleIndicatorView : View {

    companion object {
        private const val TAG = "VerticalDragAbleIndicatorView"
    }

    data class ItemModel(
        var title: String = "",
        var x: Float = 0f,
        var y: Float = 0f,
        var width: Float = 0f,
        var height: Float = 0f
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private val list = mutableListOf<ItemModel>()

    private var indicatorX = 0f
    private var indicatorY = 0f
    private var indicatorR = 0f

    var onItemSelected: ((itemModel: ItemModel) -> Unit)? = null

    private val valueAnimator = ValueAnimator().also {

        it.duration = 100
        it.addUpdateListener { valueAnimator ->

            val value = valueAnimator.animatedValue as Float
//            ILog.debug(TAG, "value $value")

            indicatorY = value

            indicatorY = when {
                value > height - indicatorR -> {
                    // can not over bottom
                    height - indicatorR
                }
                value < indicatorR -> {
                    // can not over top
                    indicatorR
                }
                else -> {
                    value
                }
            }

            invalidate()
        }
    }

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    fun initData(
        itemList: List<ItemModel>,
        itemWidthDp: Float,
        itemHeightDp: Float,
        indicatorRadiusDp: Float,
        topMarginDp: Float = 0f,
        indicatorDefaultPosition: Int = -1, // indicatorDefaultPosition should be [-1, list.size - 1]
        onItemSelected: ((itemModel: ItemModel) -> Unit)? = null
    ) {

        this.onItemSelected = onItemSelected

        list.clear()
        list.addAll(itemList)

        for (i in 0 until list.size) {

            if (i == 0) {
                list[i].title = "${list.size - i}"
                list[i].x = 0f
                list[i].y = dipToPx(topMarginDp) // top margin
                list[i].width = dipToPx(itemWidthDp)
                list[i].height = dipToPx(itemHeightDp)
            }
            else {
                list[i].title = "${list.size - i}"
                list[i].x = 0f
                list[i].y = list[i - 1].y + list[i - 1].height
                list[i].width = dipToPx(itemWidthDp)
                list[i].height = dipToPx(itemHeightDp)
            }
        }

        val lastItem = list[list.size - 1]
        layoutParams = ViewGroup.LayoutParams(dipToPx(itemWidthDp).toInt(), (lastItem.y + lastItem.height).toInt())


        indicatorR = dipToPx(indicatorRadiusDp)
        indicatorX = dipToPx(itemWidthDp * 0.5f)
        indicatorY = dipToPx(topMarginDp * 0.5f)

        if (topMarginDp == 0f) {
            indicatorY = list[0].y + list[0].height * 0.5f
        }

        if (indicatorDefaultPosition != -1) {
            indicatorY = list[indicatorDefaultPosition].y + list[indicatorDefaultPosition].height * 0.5f
        }

        this.onItemSelected?.let {

            if (indicatorDefaultPosition >=0 && indicatorDefaultPosition < list.size)
            it(list[indicatorDefaultPosition])
        }

        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = measuredWidth
        var height = measuredHeight

        width = resolveSize(width, widthMeasureSpec)
        height = resolveSize(height, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    /**
     * 顺序绘制，后面的会覆盖前面的
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBackground(canvas, Color.parseColor("#00000000"))
        drawIndicator(canvas)
        drawTexts(canvas)

    }

    private fun drawBackground(canvas: Canvas?, color: Int) {
        canvas?.drawColor(color)
    }

    private fun drawIndicator(canvas: Canvas?) {

        Paint().also {
            it.isAntiAlias = true
            it.color = Color.parseColor("#FFFFFF")
            it.strokeWidth = 8.0f
            it.style = Paint.Style.FILL

            canvas?.drawCircle(indicatorX, indicatorY, indicatorR, it)
        }
    }

    private fun drawTexts(canvas: Canvas?) {

        Paint().also {
            it.color = Color.parseColor("#111111")
            it.textSize = spToPx(15f)
            it.style = Paint.Style.FILL
            it.textAlign = Paint.Align.CENTER
            it.isAntiAlias = true

            for (i in 0 until list.size) {

                // to draw text in the center of rect
                val x = list[i].width * 0.5f

                val fontMetrics = it.fontMetrics
                val top = fontMetrics.top
                val bottom = fontMetrics.bottom
                val y = list[i].y + list[i].height * 0.5f
                val centerY = y - top * 0.5f - bottom * 0.5f

                canvas?.drawText(list[i].title, x, centerY, it)
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility", "Recycle")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {

                ILog.debug(TAG, "ACTION_DOWN $indicatorY, ${event.y}")

                valueAnimator.setFloatValues(indicatorY, correctIndicatorY(event.y))
                valueAnimator.start()
            }

            MotionEvent.ACTION_MOVE -> {

                if (valueAnimator.isRunning) {
                    return true
                }

                indicatorY = when {
                    event.y > height - indicatorR -> {
                        // can not over bottom
                        height - indicatorR
                    }
                    event.y < indicatorR -> {
                        // can not over top
                        indicatorR
                    }
                    else -> {
                        event.y
                    }
                }

                indicatorY = correctIndicatorY(indicatorY)

                invalidate()
            }

            MotionEvent.ACTION_UP -> {
            }

            else -> Unit
        }

        return true
    }

    private fun correctIndicatorY(touchedY: Float): Float {

        val firstItemCenterY = list[0].y + list[0].height * 0.5f

        // distance between item center Y and indicator Y
        var distance = abs(firstItemCenterY - touchedY)
        var itemIndex = 0

        for (i in 0 until list.size) {

            val y = list[i].y + list[i].height * 0.5f

            if (abs(y - touchedY) <= distance) {
                distance = abs(y - touchedY)
                itemIndex = i
            }
        }

        onItemSelected?.let { onItemSelected ->
            onItemSelected(list[itemIndex])
        }

        return list[itemIndex].y + list[itemIndex].height * 0.5f
    }

    private fun dipToPx(dipValue: Float): Float {
        return dipValue * context.resources.displayMetrics.density
    }

    private fun spToPx(spValue: Float): Float {
        return spValue * context.resources.displayMetrics.scaledDensity
    }
}