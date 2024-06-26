package com.swein.androidkotlintool.template.bottomtab.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.utility.activity.ActivityUtil
import com.swein.androidkotlintool.framework.utility.screen.ScreenUtil
import com.swein.androidkotlintool.template.bottomtab.fragment.TabHostFragment
import com.swein.androidkotlintool.template.navigationbar.NavigationBarTemplate

class TabHostActivity : FragmentActivity() {

    private var frameLayoutNavigationContainer: FrameLayout? = null
    private var navigationBarTemplate: NavigationBarTemplate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_host)

        initNavigationBar()

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))
        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.container, TabHostFragment())
        }
    }

    private fun initNavigationBar() {

        frameLayoutNavigationContainer = findViewById(R.id.frameLayoutNavigationContainer)
        navigationBarTemplate = NavigationBarTemplate(this).setTitle("Tab Host").hideRightButton().hideLeftButton()

        frameLayoutNavigationContainer?.addView(navigationBarTemplate?.getView())
    }
}
