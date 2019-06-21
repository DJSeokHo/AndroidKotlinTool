package com.swein.androidkotlintool

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Button
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.template.list.SHListActivity
import com.swein.androidkotlintool.template.memeberjoin.MemberJoinTemplateActivity

class MainActivity : FragmentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var buttonLoginTemplate: Button? = null
    private var buttonListTemplate: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))

        findView()
        setListener()
    }


    private fun findView() {
        buttonLoginTemplate = findViewById(R.id.buttonLoginTemplate)
        buttonListTemplate = findViewById(R.id.buttonListTemplate)
    }

    private fun setListener() {

        buttonLoginTemplate?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, MemberJoinTemplateActivity::class.java)
            }
        }

        buttonListTemplate?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, SHListActivity::class.java)
            }
        }
    }

    override fun onBackPressed() {

        finish()
    }

    override fun onDestroy() {
        ILog.debug(TAG, "onDestroy")
        super.onDestroy()
    }

    protected fun finalize() {
        // finalization logic
        ILog.debug(TAG, "finalize")
    }
}
