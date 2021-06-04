package com.swein.androidkotlintool.main.examples.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.swein.androidkotlintool.R


class TestViewTwo : View {

    private val paint = Paint()
    private val path = Path()

    init {
        paint.style = Paint.Style.STROKE
        path.lineTo(100f, 100f) // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
        path.rLineTo(100f, 0f) // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线
        // lineTo(x, y) 的参数是绝对坐标，而 rLineTo(x, y) 的参数是相对当前位置的相对坐标 （前缀 r 指的就是 relatively 「相对地」)
    }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.isAntiAlias = true

//        setColor(canvas)
//        setShaderLinearGradient(canvas)
//        setShaderRadialGradient(canvas)
//        setSweepGradient(canvas)
//        setBitmapShader(canvas)
//        setComposeShader(canvas)
//        setColorLightingColorFilter(canvas)
//        setPorterDuffColorFilter(canvas)
//        setColorMatrixColorFilter(canvas)
//        setDither(canvas)
//        setPathEffect(canvas)
        setShadowLayer(canvas)
    }

    private fun setShadowLayer(canvas: Canvas?) {

        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL

        val testTextSize = 16f
        val bounds = Rect()
        paint.getTextBounds("Seok Ho", 0, "Seok Ho".length, bounds)
        val desiredTextSize: Float = testTextSize * 300 / bounds.width()
        paint.textSize = desiredTextSize

        paint.setShadowLayer(10f,// radius 是阴影的模糊范围
            0f, 0f, // dx dy 是阴影的偏移量
            Color.RED)

        canvas?.drawText("Seok Ho", 500f, 130f, paint)
    }

    private fun setPathEffect(canvas: Canvas?) {

        val pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 10f)
        paint.pathEffect = pathEffect
        paint.color = Color.RED
        paint.strokeWidth = 20f

        canvas?.drawPath(path, paint)
    }

    // 色彩优化
    private fun setDither(canvas: Canvas?) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.camera_album)
        // 色彩优化
        paint.isDither = true
        // 在放大绘制 Bitmap 的时候就会使用双线性过滤了
        paint.isFilterBitmap = true
        canvas?.drawBitmap(bitmap, 100f, 100f, paint)
    }

    private fun setColorMatrixColorFilter(canvas: Canvas?) {
        /*
            [ a, b, c, d, e,
              f, g, h, i, j,
              k, l, m, n, o,
              p, q, r, s, t ]

            R’ = a*R + b*G + c*B + d*A + e;
            G’ = f*R + g*G + h*B + i*A + j;
            B’ = k*R + l*G + m*B + n*A + o;
            A’ = p*R + q*G + r*B + s*A + t;
         */
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.camera_album)
        val colorMatrixColorFilter = ColorMatrixColorFilter(ColorMatrix())
        paint.colorFilter = colorMatrixColorFilter

        canvas?.drawBitmap(bitmap, 100f, 100f, paint)

    }

    private fun setPorterDuffColorFilter(canvas: Canvas?) {

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.camera_album)
        val porterDuffColorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.ADD)
        paint.colorFilter = porterDuffColorFilter

        canvas?.drawBitmap(bitmap, 100f, 100f, paint)
    }
    /**
     * 为绘制设置颜色过滤。颜色过滤的意思，就是为绘制的内容设置一个统一的过滤策略，然后 Canvas.drawXXX() 方法会对每个像素都进行过滤后再绘制出来
     */
    private fun setColorLightingColorFilter(canvas: Canvas?) {

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.camera_album)
//        val lightingColorFilter: ColorFilter = LightingColorFilter(0x00ffff, 0x000000)
        val lightingColorFilter: ColorFilter = LightingColorFilter(0xffffff, 0x003000)
        paint.colorFilter = lightingColorFilter

        canvas?.drawBitmap(bitmap, 100f, 100f, paint)
    }


    /**
     * 就是把两个 Shader 一起使用
     */
    private fun setComposeShader(canvas: Canvas?) {

        // 第一个 Shader：头像的 Bitmap
        val bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.dom_hill)
        val shader1: Shader = BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        // 第二个 Shader：从上到下的线性渐变（由透明到黑色）
        val bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.camera_album)
        val shader2: Shader = BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        // ComposeShader：结合两个 Shader
        /*
        PorterDuff.Mode 是用来指定两个图像共同绘制时的颜色策略的。它是一个 enum，不同的 Mode 可以指定不同的策略。
        「颜色策略」的意思，就是说把源图像绘制到目标图像处时应该怎样确定二者结合后的颜色，
        而对于 ComposeShader(shaderA, shaderB, mode) 这个具体的方法，就是指应该怎样把 shaderB 绘制在 shaderA 上来得到一个结合后的 Shader。

        没有听说过 PorterDuff.Mode 的人，看到这里很可能依然会一头雾水：「什么怎么结合？就……两个图像一叠加，结合呗？还能怎么结合？」
        你还别说，还真的是有很多种策略来结合。

        最符合直觉的结合策略，就是我在上面这个例子中使用的 Mode: SRC_OVER。它的算法非常直观：就像上面图中的那样，把源图像直接铺在目标图像上。
        不过，除了这种，其实还有一些其他的结合方式。例如如果我把上面例子中的参数 mode 改为 PorterDuff.Mode.DST_OUT，就会变成挖空效果
         */
        val shader: Shader = ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_OVER)
        paint.shader = shader

        canvas?.drawCircle(300f, 300f, 200f, paint)
    }

    private fun setBitmapShader(canvas: Canvas?) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.dom_hill)
        val shader: Shader = BitmapShader(bitmap,
            Shader.TileMode.CLAMP, // 横向的 TileMode
            Shader.TileMode.CLAMP) // 纵向的 TileMode
        paint.shader = shader

        canvas?.drawCircle(300f, 300f, 200f, paint)
    }

    private fun setSweepGradient(canvas: Canvas?) {
        val shader = SweepGradient(300f, 300f, // 扫描的中心
            Color.parseColor("#E91E63"), // 扫描的起始颜色
            Color.parseColor("#2196F3")) // 扫描的终止颜色
        paint.shader = shader
        canvas?.drawCircle(300f, 300f, 200f, paint)
    }

    private fun setShaderRadialGradient(canvas: Canvas?) {
        val shader = RadialGradient(100f, 100f, // 辐射中心的坐标
            200f, // 辐射半径
            Color.parseColor("#E91E63"), // 辐射中心的颜色
            Color.parseColor("#2196F3"), // 辐射边缘的颜色
            Shader.TileMode.CLAMP)
        paint.shader = shader
        canvas?.drawCircle(300f, 300f, 200f, paint)
    }

    private fun setShaderLinearGradient(canvas: Canvas?) {
        // TileMode 一共有 3 个值可选： CLAMP, MIRROR 和 REPEAT。CLAMP （夹子模式？？？算了这个词我不会翻）会在端点之外延续端点处的颜色；MIRROR 是镜像模式；REPEAT 是重复模式。
        val shader = LinearGradient(100f, 100f, 500f, 500f, // 渐变的两个端点的位置
            Color.parseColor("#E91E63"), // 是端点的颜色
            Color.parseColor("#2196F3"), // 是端点的颜色
            Shader.TileMode.CLAMP)
        paint.shader = shader

//        canvas?.drawCircle(300f, 300f, 200f, paint)

        val testTextSize = 16f
        val bounds = Rect()
        paint.getTextBounds("Seok Ho", 0, "Seok Ho".length, bounds)
        val desiredTextSize: Float = testTextSize * 300 / bounds.width()
        paint.textSize = desiredTextSize

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

        canvas?.drawText("Seok Ho", 500f, 130f, paint)
    }

    /**
     Shader 这个英文单词很多人没有见过，它的中文叫做「着色器」，也是用于设置绘制颜色的。
     「着色器」不是 Android 独有的，它是图形领域里一个通用的概念，它和直接设置颜色的区别是，着色器设置的是一个颜色方案，或者说是一套着色规则。
     当设置了 Shader 之后，Paint 在绘制图形和文字时就不使用 setColor/ARGB() 设置的颜色了，而是使用 Shader 的方案中的颜色。

     在 Android 的绘制里使用 Shader ，并不直接用 Shader 这个类，而是用它的几个子类。
     具体来讲有 LinearGradient RadialGradient SweepGradient BitmapShader ComposeShader
     */
    private fun setColor(canvas: Canvas?) {
        paint.color = Color.parseColor("#009688")
        canvas?.drawRect(30f, 30f, 230f, 180f, paint)

        paint.color = Color.parseColor("#FF9800")
        canvas?.drawLine(300f, 30f, 450f, 180f, paint)

        paint.color = Color.parseColor("#E91E63")
        canvas?.drawText("Seok Ho", 500f, 130f, paint)
    }
}