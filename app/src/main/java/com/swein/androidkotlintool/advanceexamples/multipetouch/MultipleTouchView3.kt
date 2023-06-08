package com.swein.androidkotlintool.advanceexamples.multipetouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View

/**
 * 各自为战型
 * 比如画板
 */
class MultipleTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dpToPx(4f)
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        color = Color.WHITE
    }

    private val paths = SparseArray<Path>()

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {

                val actionIndex = event.actionIndex

                val path = Path()
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))

                // 需要id 来保存每根手指各自的路径，因为id不会变
                paths.append(event.getPointerId(actionIndex), path)
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {

                for (i in 0 until paths.size()) {
                    val pointerId = event.getPointerId(i)
                    val path = paths.get(pointerId) // 在 DOWN 存的时候 key是 id， 所以这里需要用 id 作为key 来取值
                    path.lineTo(event.getX(i), event.getY(i))
                }

                invalidate()
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {

                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)

                val path = paths.get(pointerId)
                path.reset()
                paths.remove(pointerId)

                invalidate()
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.BLACK)

        for (i in 0 until paths.size()) {
            val path = paths.valueAt(i)
            canvas?.drawPath(path, paint)
        }
    }

    private fun dpToPx(dpValue: Float): Float {
        return dpValue * resources.displayMetrics.density
    }
}