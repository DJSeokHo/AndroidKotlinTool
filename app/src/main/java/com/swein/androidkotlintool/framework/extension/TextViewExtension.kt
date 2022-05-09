package kr.co.dotv365.android.framework.extension

import android.graphics.Paint
import android.widget.TextView

fun TextView.underLine() {

    this.paint.flags = Paint.UNDERLINE_TEXT_FLAG
    this.paint.isAntiAlias = true

}