package com.swein.androidkotlintool.framework.util.display

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Insets
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowInsets
import android.view.WindowManager


class DisplayUtil {

    interface DisplayRootViewInfoDelegate {
        fun onInfo(
            screenWidth: Int,
            screenHeight: Int,
            statusHeight: Int,
            bottomHeight: Int,
            keypadHeight: Int
        )
    }

    companion object {

        fun getScreenWidth(activity: Activity): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics = activity.windowManager.currentWindowMetrics
                val insets: Insets = windowMetrics.windowInsets
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                windowMetrics.bounds.width() - insets.left - insets.right
            } else {
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
        }

        fun getScreenHeight(activity: Activity): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics = activity.windowManager.currentWindowMetrics
                val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                windowMetrics.bounds.height() - insets.bottom - insets.top
            } else {
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
        }

        fun getScreenWidthPx(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

        fun getScreenHeightPx(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

        /**
         * put is in onResume() or onCreate
         */
        fun keepScreenOn(activity: Activity) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        /**
         * put this in onPause() or onDestroy()
         */
        fun unKeepScreenOn(activity: Activity) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

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

        fun getRootViewInfo(
            rootView: View,
            displayRootViewInfoDelegate: DisplayRootViewInfoDelegate
        ) {
            rootView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val r = Rect()
                    rootView.getWindowVisibleDisplayFrame(r)
                    val screenWidth = rootView.rootView.width
                    val screenHeight = rootView.rootView.height
                    var statusHeight = 0
                    val statusResourceId = rootView.context.resources.getIdentifier(
                        "status_bar_height",
                        "dimen",
                        "android"
                    )
                    if (statusResourceId > 0) {
                        statusHeight =
                            rootView.context.resources.getDimensionPixelSize(statusResourceId)
                    }
                    var bottomHeight = 0
                    val bottomResourceId = rootView.context.resources.getIdentifier(
                        "navigation_bar_height",
                        "dimen",
                        "android"
                    )
                    if (bottomResourceId > 0) {
                        bottomHeight =
                            rootView.context.resources.getDimensionPixelSize(bottomResourceId)
                    }
                    val keypadHeight = screenHeight - r.bottom
                    displayRootViewInfoDelegate.onInfo(
                        screenWidth,
                        screenHeight,
                        statusHeight,
                        bottomHeight,
                        keypadHeight
                    )
                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

}