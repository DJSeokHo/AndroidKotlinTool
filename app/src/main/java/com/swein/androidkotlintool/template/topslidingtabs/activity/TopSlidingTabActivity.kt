package com.swein.androidkotlintool.template.topslidingtabs.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.widget.FrameLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.template.navigationbar.NavigationBarTemplate
import com.swein.androidkotlintool.template.topslidingtabs.activity.adapter.SlidingTabViewPagerAdapter
import com.swein.androidkotlintool.template.topslidingtabs.activity.adapter.fragment.SlidingTabItemFragment
import com.swein.androidkotlintool.template.topslidingtabs.view.SlidingTabLayout

class TopSlidingTabActivity : FragmentActivity() {

    companion object {
        private const val TAG = "TopSlidingTabActivity"
    }

    private var slidingTabLayout: SlidingTabLayout? = null
    private var slidingViewPager: ViewPager? = null
    private var slidingTabViewPagerAdapter: SlidingTabViewPagerAdapter? = null

    private var list: MutableList<Fragment>? = null

    private var frameLayoutNavigationContainer: FrameLayout? = null
    private var navigationBarTemplate: NavigationBarTemplate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_sliding_tab)

        ScreenUtil.setTitleBarColor(this, Constants.APP_BASIC_THEME_COLOR)

        findView()
        initNavigationBar()
        initData()
        updateView()
    }

    private fun initData() {
        list = mutableListOf()

        list?.let {
            var itemFragment: SlidingTabItemFragment?
            for(index in 0..5) {
                itemFragment = SlidingTabItemFragment()
                it.add(itemFragment)
                itemFragment.setText("Fragment $index")
            }

            slidingTabViewPagerAdapter = SlidingTabViewPagerAdapter(supportFragmentManager, list!!)
            updateView()
        }

    }

    private fun findView() {
        slidingTabLayout = findViewById(R.id.slidingTabLayout)
        slidingViewPager = findViewById(R.id.slidingViewPager)

        frameLayoutNavigationContainer = findViewById(R.id.frameLayoutNavigationContainer)
    }

    private fun initNavigationBar() {

        navigationBarTemplate = NavigationBarTemplate(this).setTitle("Top Tab").hideRightButton().hideLeftButton()

        frameLayoutNavigationContainer?.addView(navigationBarTemplate?.getView())
    }

    private fun updateView() {
        slidingViewPager?.adapter = slidingTabViewPagerAdapter
        slidingTabLayout?.setViewPager(slidingViewPager)
    }

}
