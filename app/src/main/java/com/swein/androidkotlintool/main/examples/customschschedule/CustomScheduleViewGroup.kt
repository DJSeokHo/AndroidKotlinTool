package com.swein.androidkotlintool.main.examples.customschschedule

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import com.swein.androidkotlintool.framework.util.log.ILog

class CustomScheduleViewGroup: ViewGroup {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 1073742904 1073744002
//        ILog.debug("???", "onMeasure $widthMeasureSpec $heightMeasureSpec")
//        val width = MeasureSpec.getSize(widthMeasureSpec)
//        val height = MeasureSpec.getSize(heightMeasureSpec)
//        ILog.debug("???", "onMeasure $width $height")
//
//        measureChildren(widthMeasureSpec,heightMeasureSpec)

        //先取出SimpleFlowLayout的父view 对SimpleFlowLayout 的测量限制 这一步很重要噢。
        //你只有知道自己的宽高 才能限制你子view的宽高
        /*
        不限制（UNSPECIFIED）
        还是限制个最大值（AT_MOST），让子view不超过这个值
        还是直接限制死，我让你是多少就得是多少（EXACTLY）
        public static final int AT_MOST = -2147483648;
        public static final int EXACTLY = 1073741824;
        public static final int UNSPECIFIED = 0;
         */
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        ILog.debug("???", "with mode is $widthMode")

        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)


        var usedWidth = 0      //已使用的宽度
        var remaining = 0      //剩余可用宽度
        var totalHeight = 0    //总高度
        var lineHeight = 0     //当前行高

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val lp = childView.layoutParams
            ILog.debug("????", "onMeasure $i")
            //先测量子view
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            //然后计算一下宽度里面 还有多少是可用的 也就是剩余可用宽度
            remaining = widthSize - usedWidth

            //如果一行不够放了，也就是说这个子view测量的宽度 大于 这一行 剩下的宽度的时候 我们就要另外启一行了
            if (childView.getMeasuredWidth() > remaining) {
                //另外启动一行的时候，使用过的宽度 当然要设置为0
                usedWidth = 0
                //另外启动一行了 我们的总高度也要加一下，不然高度就不对了
                totalHeight += lineHeight
            }

            //已使用 width 进行 累加
            usedWidth += childView.measuredWidth
            //当前 view 的高度
            lineHeight = childView.measuredHeight
        }

        //如果SimpleFlowLayout 的高度 为wrap cotent的时候 才用我们叠加的高度，否则，我们当然用父view对如果SimpleFlowLayout 限制的高度
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = totalHeight
        }

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(p0: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var childTop = 0
        var childLeft = 0
        var childRight = 0
        var childBottom = 0

        //已使用 width
        var usedWidth = 0


        //customlayout 自己可使用的宽度
        val layoutWidth = measuredWidth
//        Log.v("wuyue", "layoutWidth==" + layoutWidth)
        for (i in 0 until childCount) {

            ILog.debug("????", "onLayout $i")
            val childView = getChildAt(i)
            //取得这个子view要求的宽度和高度
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight

            //如果宽度不够了 就另外启动一行
            if (layoutWidth - usedWidth < childWidth) {
                childLeft = 0
                usedWidth = 0
                childTop += childHeight
                childRight = childWidth
                childBottom = childTop + childHeight
                childView.layout(0, childTop, childRight, childBottom)
                usedWidth += childWidth
                childLeft = childWidth
                continue
            }
            childRight = childLeft + childWidth
            childBottom = childTop + childHeight
            childView.layout(childLeft, childTop, childRight, childBottom)
            childLeft += childWidth
            usedWidth += childWidth

        }

    }
}