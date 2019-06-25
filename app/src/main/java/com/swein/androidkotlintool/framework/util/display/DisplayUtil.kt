package com.swein.androidkotlintool.framework.util.display

import android.content.Context
import android.content.res.Configuration

class DisplayUtil {

    companion object {

        fun isLandscape(context: Context): Boolean {
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }

        /**
         * px value to dip or dp
         *
         * @param context
         * @param pxValue
         * @return
         */
        fun pxToDip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density

            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * dip or dp to px value
         * @param context
         * @param dipValue
         * @return
         */
        fun dipToPx(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

        /**
         * dip or dp to sp value
         * @param context
         * @param pxValue
         * @return
         */
        fun pxToSp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * sp value to dip or dp
         *
         * @param context
         * @param spValue
         * @return
         */
        fun spToPx(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }
    }

}