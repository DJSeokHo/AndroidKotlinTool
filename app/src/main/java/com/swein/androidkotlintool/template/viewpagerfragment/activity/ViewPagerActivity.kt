package com.swein.androidkotlintool.template.viewpagerfragment.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.template.navigationbar.NavigationBarTemplate
import com.swein.androidkotlintool.template.viewpagerfragment.fragment.ViewPagerFragment

class ViewPagerActivity : FragmentActivity() {

    private var frameLayoutNavigationContainer: FrameLayout? = null
    private var navigationBarTemplate: NavigationBarTemplate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        ScreenUtil.setTitleBarColor(this, Constants.APP_BASIC_THEME_COLOR)
        initNavigationBar()

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.container, ViewPagerFragment())
        }
    }

    private fun initNavigationBar() {

        frameLayoutNavigationContainer = findViewById(R.id.frameLayoutNavigationContainer)
        navigationBarTemplate = NavigationBarTemplate(this).setTitle("View Pager").hideRightButton().hideLeftButton()

        frameLayoutNavigationContainer?.addView(navigationBarTemplate?.getView())
    }
}
