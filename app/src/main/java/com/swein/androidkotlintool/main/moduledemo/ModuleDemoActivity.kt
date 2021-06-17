package com.swein.androidkotlintool.main.moduledemo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil

class ModuleDemoActivity : FragmentActivity() {

    companion object {
        private const val TAG = "ModuleDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_demo)

        ScreenUtil.setTitleBarColor(this, Constants.APP_BASIC_THEME_COLOR)

    }

}
