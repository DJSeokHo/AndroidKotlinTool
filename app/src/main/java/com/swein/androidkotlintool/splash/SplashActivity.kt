package com.swein.androidkotlintool.splash

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.swein.androidkotlintool.main.MainActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtility

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))

        countDownToFinish()
    }

    private fun countDownToFinish() {

        ThreadUtility.startThread {

            ThreadUtility.startUIThread(0) {
                ActivityUtil.startNewActivityWithFinish(this, MainActivity::class.java)
            }
        }
    }

    override fun onBackPressed() {
    }
}
