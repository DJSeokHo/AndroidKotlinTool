package com.swein.androidkotlintool.main.examples.customview

import android.app.ActionBar
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.swein.androidkotlintool.framework.util.log.ILog
import java.util.jar.Attributes

class TestViewOne : View {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }

    private val paint = Paint()
    private val path = Path()

//    init {
//        path.addArc(200f, 200f, 400f, 400f, -225f, 225f)
//        path.arcTo(400f, 200f, 600f, 400f, -180f, 225f, false)
//        path.lineTo(400f, 542f)
//    }

//    init {
//        path.addCircle(300f, 300f, 200f, Path.Direction.CW)
//    }

//    init {
//        paint.style = Paint.Style.STROKE
//        path.lineTo(100f, 100f) // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
//        path.rLineTo(100f, 0f) // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线
//        // lineTo(x, y) 的参数是绝对坐标，而 rLineTo(x, y) 的参数是相对当前位置的相对坐标 （前缀 r 指的就是 relatively 「相对地」)
//    }

//    init {
//
//        paint.style = Paint.Style.STROKE
//        path.lineTo(100f, 100f) // 画斜线
//        path.moveTo(200f, 100f) // 我移~~
//        path.lineTo(200f, 0f) // 画竖线
//    }

//    init {
//        paint.style = Paint.Style.STROKE
//        path.lineTo(100f, 100f)
//        path.arcTo(100f, 100f, 300f, 300f, -90f, 90f, true) // 强制移动到弧形起点（无痕迹）
//
////        paint.style = Paint.Style.STROKE
////        path.lineTo(100f, 100f)
////        path.arcTo(100f, 100f, 300f, 300f, -90f, 90f, false) // 直接连线连到弧形起点（有痕迹）
//    }

    init {

        paint.style = Paint.Style.STROKE
        path.moveTo(100f, 100f)
        path.lineTo(200f, 100f)
        path.lineTo(150f, 150f)
        path.close() // 使用 close() 封闭子图形。等价于 path.lineTo(100, 100)
    }
    /**
     * 顺序绘制，后面的会覆盖前面的
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.isAntiAlias = true

        drawBackground(canvas)
        drawCircle(canvas)
//        drawTwoRect(canvas)
//        drawPoint(canvas)
//        drawPoints(canvas)
//        drawOval(canvas)
//        drawLine(canvas)
//        drawLines(canvas)
//        drawRoundRect(canvas)
//        drawArc(canvas)
//        drawPath(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 这里不需要 super 方法

        var width = measuredWidth
        var height = measuredHeight

        ILog.debug("???", "$width $height")

//        width = 400
//        height = 400

        width = resolveSize(width, widthMeasureSpec)
        height = resolveSize(height, heightMeasureSpec)

        ILog.debug("???!!!", "$width $height")

        setMeasuredDimension(width, height)
    }

    private fun drawPath(canvas: Canvas?) {
        paint.color = Color.RED
        canvas?.drawPath(path, paint)
    }

    /**
     * drawArc() 是使用一个椭圆来描述弧形的。left, top, right, bottom 描述的是这个弧形所在的椭圆；
     * startAngle 是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度），
     * sweepAngle 是弧形划过的角度；useCenter 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形。
     */
    private fun drawArc(canvas: Canvas?) {

        paint.color = Color.RED
        paint.strokeWidth = 3f

        paint.style = Paint.Style.FILL // 填充模式
        canvas?.drawArc(200f, 100f, 800f, 500f, -110f, 100f, true, paint) // 绘制扇形
        canvas?.drawArc(200f, 100f, 800f, 500f, 20f, 140f, false, paint) // 绘制弧形
        paint.style = Paint.Style.STROKE // 画线模式
        canvas?.drawArc(200f, 100f, 800f, 500f, 180f, 60f, false, paint) // 绘制不封口的弧形

    }

    private fun drawRoundRect(canvas: Canvas?) {
        paint.color = Color.RED
        paint.strokeWidth = 3f
        canvas?.drawRoundRect(100f, 100f, 500f, 300f, 50f, 50f, paint)
    }

    private fun drawLines(canvas: Canvas?) {

        val points = floatArrayOf(20f, 20f, 120f, 20f, 70f, 20f, 70f, 120f, 20f, 120f, 120f, 120f, 150f, 20f, 250f, 20f, 150f, 20f, 150f, 120f, 250f, 20f, 250f, 120f, 150f, 120f, 250f, 120f)
        paint.color = Color.RED
        paint.strokeWidth = 3f

        // 设置线头的形状
        // BUTT 平头、ROUND 圆头、SQUARE 方头
//        paint.strokeCap = Paint.Cap.BUTT
        paint.strokeCap = Paint.Cap.ROUND
//        paint.strokeCap = Paint.Cap.SQUARE

        // 设置拐角的形状
        // MITER 尖角、 BEVEL 平角和 ROUND 圆角
//        paint.strokeJoin = Paint.Join.MITER
//        paint.strokeJoin = Paint.Join.BEVEL
        paint.strokeJoin = Paint.Join.ROUND

        canvas?.drawLines(points, paint)

    }

    private fun drawLine(canvas: Canvas?) {
        paint.color = Color.RED
        paint.strokeWidth = 3f
        canvas?.drawLine(200f, 200f, 800f, 500f, paint)
    }

    private fun drawOval(canvas: Canvas?) {

        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        canvas?.drawOval(50f, 50f, 350f, 200f, paint)

        paint.style = Paint.Style.STROKE
        canvas?.drawOval(400f, 50f, 700f, 200f, paint)
    }

    private fun drawPoints(canvas: Canvas?) {
        val points = floatArrayOf(0f, 0f, 50f, 50f, 50f, 100f, 100f, 50f, 100f, 100f, 150f, 50f, 150f, 100f)
        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)

        paint.color = Color.RED
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
            8 /* 一共绘制 8 个数（4 个点）*/, paint)
    }

    private fun drawPoint(canvas: Canvas?) {
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND
//        paint.strokeCap = Paint.Cap.SQUARE
//        paint.strokeCap = Paint.Cap.BUTT
        canvas?.drawPoint(50f, 50f, paint)
    }

    private fun drawTwoRect(canvas: Canvas?) {

        paint.style = Paint.Style.FILL
        canvas?.drawRect(100f, 100f, 500f, 500f, paint)

        paint.style = Paint.Style.STROKE
        canvas?.drawRect(700f, 100f, 1100f, 500f, paint)
    }

    private fun drawCircle(canvas: Canvas?) {
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        paint.strokeWidth = 2.0f

        canvas?.drawCircle(200f, 200f, 200f, paint)
    }

    private fun drawBackground(canvas: Canvas?) {

        canvas?.drawColor(Color.parseColor("#333333"))
//        canvas?.drawRGB()
//        canvas?.drawARGB()
    }

}