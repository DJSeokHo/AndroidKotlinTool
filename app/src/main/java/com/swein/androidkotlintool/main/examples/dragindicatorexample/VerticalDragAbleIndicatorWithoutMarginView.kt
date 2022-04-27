package com.swein.androidkotlintool.main.examples.dragindicatorexample

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class VerticalDragAbleIndicatorWithoutMarginView : View {

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

    private val paint = Paint()

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

    fun initData(
        itemList: List<ItemModel>,
        itemWidthDp: Float,
        itemHeightDp: Float,
        indicatorRadiusDp: Float,
        indicatorDefaultPosition: Int = -1, // indicatorDefaultPosition should be [-1, list.size - 1]
        onItemSelected: ((itemModel: ItemModel) -> Unit)? = null
    ) {

        this.onItemSelected = onItemSelected

        list.clear()
        list.addAll(itemList)

        // x, y, width, height is a area for title text.
        // we will draw the text in the center of the area.
        for (i in 0 until list.size) {

            list[i].title = "$i"
            list[i].x = 0f
            list[i].y = if (i == 0) {
                dipToPx(0f)
            }
            else {
                list[i - 1].y + list[i - 1].height
            }

            list[i].width = dipToPx(itemWidthDp)
            list[i].height = dipToPx(itemHeightDp)

        }

        val lastItem = list[list.size - 1]

        layoutParams.width = dipToPx(itemWidthDp).toInt()
        layoutParams.height = (lastItem.y + lastItem.height).toInt()

        indicatorR = dipToPx(indicatorRadiusDp)
        indicatorX = dipToPx(itemWidthDp * 0.5f)
        indicatorY = list[0].y + list[0].height * 0.5f

        if (indicatorDefaultPosition != -1) {
            indicatorY = list[indicatorDefaultPosition].y + list[indicatorDefaultPosition].height * 0.5f
        }

        this.onItemSelected?.let {

            if (indicatorDefaultPosition >=0 && indicatorDefaultPosition < list.size) {
                it(list[indicatorDefaultPosition])
            }
            else {
                it(list[0])
            }
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

        // do not create object in onDraw() !!!!

        // draw order is important

        // background is on the bottom
        drawBackground(canvas, Color.parseColor("#00000000"))

        // indicator will over the background
        drawIndicator(canvas)

        // text will over the indicator
        drawTexts(canvas)

    }

    private fun drawBackground(canvas: Canvas?, color: Int) {
        canvas?.drawColor(color)
    }

    private fun drawIndicator(canvas: Canvas?) {

        paint.isAntiAlias = true
        paint.color = Color.parseColor("#FFFFFF")
        paint.strokeWidth = 8.0f
        paint.style = Paint.Style.FILL

        canvas?.drawCircle(indicatorX, indicatorY, indicatorR, paint)
    }

    private fun drawTexts(canvas: Canvas?) {

        paint.color = Color.parseColor("#111111")
        paint.textSize = spToPx(15f)
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
        paint.isAntiAlias = true

        for (i in 0 until list.size) {

            // to draw text in the center of rect
            val x = list[i].width * 0.5f

            val fontMetrics = paint.fontMetrics
            val top = fontMetrics.top
            val bottom = fontMetrics.bottom
            val y = list[i].y + list[i].height * 0.5f
            val centerY = y - top * 0.5f - bottom * 0.5f

            canvas?.drawText(list[i].title, x, centerY, paint)
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