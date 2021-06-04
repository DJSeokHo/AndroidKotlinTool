package com.swein.androidkotlintool.main.examples.customview.customlayout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class CLImageView: ImageView {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 先执行测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 获取已测量的结果
        var measuredWidth = measuredWidth
        var measuredHeight = measuredHeight

        // 更改
        if (measuredWidth > measuredHeight) {
            measuredHeight = measuredWidth
        }
        else {
            measuredWidth = measuredHeight
        }

        // 保存更改结果
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

}