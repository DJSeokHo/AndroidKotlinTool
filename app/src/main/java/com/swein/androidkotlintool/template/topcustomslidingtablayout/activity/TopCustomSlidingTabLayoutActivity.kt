package com.swein.androidkotlintool.template.topcustomslidingtablayout.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.template.topcustomslidingtablayout.fragments.SubTabLayoutFragment
import java.util.ArrayList

class TopCustomSlidingTabLayoutActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    private var fragmentList: MutableList<Fragment>? = null
    private var tabTitleList: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_custom_sliding_tab_layout)

        ScreenUtil.setTitleBarColor(this, Constants.APP_BASIC_THEME_COLOR)

        findView()
    }

    private fun findView() {
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabTitleList = mutableListOf()

        tabTitleList?.let {
            it.add("fragment 1")
            it.add("fragment 2")
            it.add("fragment 3")
            it.add("fragment 4")
            it.add("fragment 5")
            it.add("fragment 6")
        }

        fragmentList = mutableListOf()

        for(i in 0 until tabTitleList!!.size) {

            val subTabLayoutFragment = SubTabLayoutFragment()
            fragmentList!!.add(subTabLayoutFragment)
        }

        viewPager?.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitleList!!.get(position)
            }

            override fun getCount(): Int {
                return fragmentList!!.size
            }

            override fun getItem(arg0: Int): android.support.v4.app.Fragment {
                return fragmentList!!.get(arg0)
            }
        }

        //        tabLayout.setTabMode (TabLayout.MODE_FIXED);
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.setSelectedTabIndicatorColor(Color.BLUE)
    }
}
