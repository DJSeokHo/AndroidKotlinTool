package com.swein.androidkotlintool.splash

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.swein.androidkotlintool.MainActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))

        countDownToFinish()
    }

    private fun countDownToFinish() {

        ThreadUtil.startThread(Runnable {

            ThreadUtil.startUIThread(2000, Runnable {
                ActivityUtil.startNewActivityWithFinish(this, MainActivity::class.java)
            })
        })
    }

    override fun onBackPressed() {
    }
}
