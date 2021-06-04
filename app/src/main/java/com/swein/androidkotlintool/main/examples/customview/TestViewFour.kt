package com.swein.androidkotlintool.main.examples.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
绘制过程中最典型的两个部分是上面讲到的主体和子 View，但它们并不是绘制过程的全部。除此之外，绘制过程还包含一些其他内容的绘制。具体来讲，一个完整的绘制过程会依次绘制以下几个内容：

背景
主体（onDraw()）
子 View（dispatchDraw()）
滑动边缘渐变和滑动条
前景
 */
class TestViewFour : View {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


    }

    // 怎样才能让 LinearLayout 的绘制内容盖住子 View 呢？只要让它的绘制代码在子 View 的绘制之后再执行就好了
    // 只要重写 dispatchDraw()，并在 super.dispatchDraw() 的下面写上你的绘制代码，
    // 这段绘制代码就会发生在子 View 的绘制之后，从而让绘制内容盖住子 View 了
    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }

    // 滑动边缘渐变和滑动条以及前景，这两部分被合在一起放在了 onDrawForeground() 方法里
    /*
    滑动边缘渐变和滑动条可以通过 xml 的 android:scrollbarXXX 系列属性或 Java 代码的 View.setXXXScrollbarXXX() 系列方法来设置；
    前景可以通过 xml 的 android:foreground 属性或 Java 代码的 View.setForeground() 方法来设置。
    而重写 onDrawForeground() 方法，并在它的 super.onDrawForeground() 方法的上面或下面插入绘制代码，
    则可以控制绘制内容和滑动边缘渐变、滑动条以及前景的遮盖关系
     */
    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
    }
}