package com.swein.androidkotlintool.main.examples.customschedule.customscheduleview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.customschedule.customscheduleview.item.ScheduleViewHolder

class CustomScheduleViewGroup: ViewGroup {

    companion object {
        private const val COLUMN_COUNT = 3
    }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

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
        ILog.debug("???", "with mode is $widthMode, height mode is $heightMode")

        val viewGroupWidth = MeasureSpec.getSize(widthMeasureSpec)
        var viewGroupHeight = MeasureSpec.getSize(heightMeasureSpec)


//        var usedWidth = 0      // 已使用的宽度
//        var totalHeight = 0    // 总高度

//        var lineHeight = 0     // 当前行高

        for (i in 0 until childCount) {

            val childView = getChildAt(i)

            //先测量子view
            ILog.debug("????", "measureChild $i")
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)

            //然后计算一下宽度里面 还有多少是可用的 也就是剩余可用宽度
//            val remainingWidth = viewGroupWidth - usedWidth

            //如果一行不够放了，也就是说这个子view测量的宽度 大于 这一行 剩下的宽度的时候 我们就要另外启一行了
//            if (childView.measuredWidth > remainingWidth) {

                //另外启动一行的时候，使用过的宽度 当然要设置为0
//                usedWidth = 0

                //另外启动一行了 我们的总高度也要加一下，不然高度就不对了
//                totalHeight += lineHeight

//            }

            //已使用 width 进行 累加
//            usedWidth += childView.measuredWidth
            //当前 view 的高度
//            lineHeight = childView.measuredHeight
        }

        //如果ViewGroup的高度为wrap_content的时候, 才用我们叠加的高度，否则，我们当然用父view对如果SimpleFlowLayout 限制的高度
//        if (heightMode == MeasureSpec.AT_MOST) {
//            viewGroupHeight = totalHeight
//        }

        setMeasuredDimension(viewGroupWidth, viewGroupHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        for (i in 0 until childCount) {

            val childView = getChildAt(i) as ScheduleViewHolder

            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight

            val addEnableColumnIndex = findAddEnableColumnIndex(childView)
            ILog.debug("???", "can add to column $addEnableColumnIndex")
            childView.column = addEnableColumnIndex

            val childTop = childView.positionY
            val childBottom = childTop + childHeight
            val childLeft = childWidth * addEnableColumnIndex
            val childRight = childLeft + childWidth

            childView.layout(childLeft, childTop, childRight, childBottom)
        }
    }

    private fun getOnLayoutChildViewList(): MutableList<ScheduleViewHolder> {

        val list = mutableListOf<ScheduleViewHolder>()

        for (i in 0 until childCount) {
            val childView = getChildAt(i) as ScheduleViewHolder
            if (childView.column != -1) {
                list.add(childView)
            }
        }

        return list
    }

    private fun getBottomBorderInEveryColumn(): MutableList<Int> {

        val borderList = mutableListOf<Int>()

        for (i in 0 until COLUMN_COUNT) {
            borderList.add(0)
        }

        val onLayoutChildViewList = getOnLayoutChildViewList()

        if (onLayoutChildViewList.size > COLUMN_COUNT) {

            for (i in 0 until onLayoutChildViewList.size) {

                val currentChildView = onLayoutChildViewList[i]
                val currentBottomBorder = currentChildView.positionY + currentChildView.measuredHeight
                if (borderList[currentChildView.column] < currentBottomBorder) {
                    borderList[currentChildView.column] = currentBottomBorder
                }
            }
        }
        else {

            for (i in 0 until onLayoutChildViewList.size) {
                val childView = onLayoutChildViewList[i]
                borderList[childView.column] = childView.positionY + childView.measuredHeight
            }

        }

        return borderList
    }

    private fun findAddEnableColumnIndex(viewWillLayout: ScheduleViewHolder): Int {

        var index = 0

        val bottomBorderList = getBottomBorderInEveryColumn()

        ILog.debug("~~~", "bottom border list")
        for (bottomBorder in bottomBorderList) {
            ILog.debug("~~~", "$bottomBorder")
        }
        ILog.debug("~~~", "bottom border list")

        for (i in 0 until bottomBorderList.size) {

            if (viewWillLayout.positionY >= bottomBorderList[i]) {
                index = i
                break
            }
        }

        return index
    }
}