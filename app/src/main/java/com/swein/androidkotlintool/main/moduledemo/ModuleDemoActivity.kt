package com.swein.androidkotlintool.main.moduledemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.volley.VolleyError
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.module.volley.VolleyModule
import com.swein.androidkotlintool.framework.module.volley.VolleyModuleDelegate
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil

class ModuleDemoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ModuleDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_demo)

        ScreenUtil.setTitleBarColor(this, Constants.APP_BASIC_THEME_COLOR)

        volleyModuleDemo()
    }

    private fun volleyModuleDemo() {

        ThreadUtil.startThread(Runnable {
            val volleyModule = VolleyModule(this)
            volleyModule.requestUrlGet("https://m.baidu.com/", object: VolleyModuleDelegate {

                override fun onResponse(response: String) {
                    ILog.debug(TAG, response)
                }

                override fun onErrorResponse(error: VolleyError) {
                    ILog.debug(TAG, error.message)
                }
            })
        })

    }

}