package com.swein.androidkotlintool.framework.util.screen

import android.view.WindowManager
import android.view.Window.FEATURE_NO_TITLE
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.Window
import com.swein.androidkotlintool.constants.Constants


class ScreenUtil {
    companion object {

        /**
         *
         * put this before setContentView of Activity
         *
         * without flash actionbar
         *
         * @param activity
         */

        fun hideTitleBarWithFullScreen(activity: Activity) {
            // hide title bar
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
            // hide state bar
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        fun setTitleBarColor(activity: Activity) {

            if (Build.VERSION.SDK_INT >= 21) {
                activity.window.statusBarColor = Color.parseColor(Constants.APP_BASIC_THEME_COLOR)
            }
        }
    }
}