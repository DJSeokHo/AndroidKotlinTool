package com.swein.androidkotlintool.framework.utility.dimension

import android.content.Context
import android.util.TypedValue

class DimensionUtil {

    companion object {

        /**
         * dpi - > px
         */
        fun dimension(value: Int, context: Context?): Int {
            return if (context == null) {
                -1
            } else TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources
                    .displayMetrics
            ).toInt()

        }
    }
}