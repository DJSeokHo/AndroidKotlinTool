package com.swein.androidkotlintool.framework.util.theme

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.WindowManager

class ThemeUtil {

    companion object {

        /**
         * must > API 19
         * put this before setContentView()
         *
         * and add
         * android:fitsSystemWindows="true"
         * to your root layout of xml file
         *
         * @param activity activity
         */
        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun transWindow(activity: Activity) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        /**
         * must > API 19
         * put this before setContentView()
         *
         * and add
         * android:fitsSystemWindows="true"
         * to your root layout of xml file
         *
         *
         * @param activity
         * @param colorResId
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun setWindowStatusBarColorResource(activity: Activity, colorResId: Int) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(colorResId)
        }

        /**
         * must > API 19
         * put this before setContentView()
         *
         * and add
         * android:fitsSystemWindows="true"
         * to your root layout of xml file
         *
         *
         * @param activity
         * @param color
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun setWindowStatusBarColor(activity: Activity, color: Int) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }
}