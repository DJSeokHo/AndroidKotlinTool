package com.swein.androidkotlintool

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Button
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.template.bottomtab.activity.TabHostActivity
import com.swein.androidkotlintool.template.list.SHListActivity
import com.swein.androidkotlintool.template.memeberjoin.MemberJoinTemplateActivity
import com.swein.androidkotlintool.template.topslidingtabs.activity.TopSlidingTabActivity

class MainActivity : FragmentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var buttonLoginTemplate: Button? = null
    private var buttonListTemplate: Button? = null
    private var buttonTabHost: Button? = null
    private var buttonTopSlidingTab: Button? = null

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
        buttonTabHost = findViewById(R.id.buttonTabHost)
        buttonTopSlidingTab = findViewById(R.id.buttonTopSlidingTab)
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

        buttonTabHost?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, TabHostActivity::class.java)
            }
        }

        buttonTopSlidingTab?.let {
            it.setOnClickListener {
                ActivityUtil.startNewActivityWithoutFinish(this, TopSlidingTabActivity::class.java)
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
