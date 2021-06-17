package com.swein.androidkotlintool.main.examples.materialdesignbottombar

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.toast.ToastUtility

class MaterialDesignBottomBarExampleActivity : FragmentActivity() {

    companion object {
        private const val HOME_TAB = "HOME_TAB"
        private const val DISCOVER_TAB = "DISCOVER_TAB"
        private const val NOTIFICATION_TAB = "NOTIFICATION_TAB"
        private const val PROFILE_TAB = "PROFILE_TAB"
    }

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingActionButton)
    }

    private val linearLayoutHome: LinearLayout by lazy {
        findViewById(R.id.linearLayoutHome)
    }
    private val linearLayoutProfile: LinearLayout by lazy {
        findViewById(R.id.linearLayoutProfile)
    }
    private val linearLayoutNotification: LinearLayout by lazy {
        findViewById(R.id.linearLayoutNotification)
    }
    private val linearLayoutDiscover: LinearLayout by lazy {
        findViewById(R.id.linearLayoutDiscover)
    }

    private val imageViewHome: ImageView by lazy {
        findViewById(R.id.imageViewHome)
    }
    private val imageViewDiscover: ImageView by lazy {
        findViewById(R.id.imageViewDiscover)
    }
    private val imageViewNotification: ImageView by lazy {
        findViewById(R.id.imageViewNotification)
    }
    private val imageViewProfile: ImageView by lazy {
        findViewById(R.id.imageViewProfile)
    }

    private val bottomAppBar: BottomAppBar by lazy {
        findViewById(R.id.bottomAppBar)
    }

    private var currentTapTag = ""

    private var bottomAppBarState = 0 // 0: none, 1: shown, 2: hidden

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design_bottom_bar_example)

        linearLayoutHome.setOnClickListener {
            ILog.debug("???", "home")
            changeTab(HOME_TAB)
        }
        linearLayoutDiscover.setOnClickListener {
            ILog.debug("???", "discover")
            changeTab(DISCOVER_TAB)
        }
        linearLayoutNotification.setOnClickListener {
            ILog.debug("???", "notification")
            changeTab(NOTIFICATION_TAB)
        }
        linearLayoutProfile.setOnClickListener {
            ILog.debug("???", "profile")
            changeTab(PROFILE_TAB)
        }

        floatingActionButton.setOnClickListener {
            ILog.debug("???", "center menu ?? $bottomAppBarState")

            if (bottomAppBarState == 0 || bottomAppBarState == 1) {
                ToastUtility.showCustomShortToastNormal(this, "center menu")
            }
            else {

                if (currentTapTag == "") {
                    return@setOnClickListener
                }

                val fragment = supportFragmentManager.findFragmentByTag(currentTapTag) as MaterialDesignBottomBarExampleFragment?
                fragment?.backToTop()

            }
        }

        val params = bottomAppBar.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = object : BottomAppBar.Behavior() {

            override fun slideDown(child: BottomAppBar) {
                super.slideDown(child)

                if (bottomAppBarState == 2) {
                    return
                }

                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                floatingActionButton.setImageResource(R.drawable.icon_scroll_up)
                bottomAppBarState = 2
                ILog.debug("???", "hidden")
            }

            override fun slideUp(child: BottomAppBar) {
                super.slideUp(child)

                if (bottomAppBarState == 1) {
                    return
                }

                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                floatingActionButton.setImageResource(R.drawable.menu)
                bottomAppBarState = 1
                ILog.debug("???", "shown")
            }
        }

        changeTab(HOME_TAB)
    }

    private fun changeTab(tab: String) {

        if (tab == currentTapTag) {
            return
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        resetTabBarStyle()

        val prevFragment = supportFragmentManager.findFragmentByTag(currentTapTag)
        if (prevFragment != null) {
            transaction.hide(prevFragment)
        }

        currentTapTag = tab
        var currentFragment = supportFragmentManager.findFragmentByTag(currentTapTag)

        when (tab) {
            HOME_TAB -> {

                if (currentFragment == null) {
                    currentFragment = MaterialDesignBottomBarExampleFragment.newInstance("home")
                }

                if (currentFragment.isAdded) {
                    transaction.show(currentFragment)
                }
                else {
                    transaction.add(R.id.container, currentFragment, currentTapTag)
                }

                imageViewHome.setImageResource(R.mipmap.ti_home_selected)

            }
            DISCOVER_TAB -> {

                if (currentFragment == null) {
                    currentFragment = MaterialDesignBottomBarExampleFragment.newInstance("discover")
                }

                if (currentFragment.isAdded) {
                    transaction.show(currentFragment)
                }
                else {
                    transaction.add(R.id.container, currentFragment, currentTapTag)
                }

                imageViewDiscover.setImageResource(R.mipmap.ti_discover_selected)

            }
            NOTIFICATION_TAB -> {
                if (currentFragment == null) {
                    currentFragment = MaterialDesignBottomBarExampleFragment.newInstance("notification")
                }

                if (currentFragment.isAdded) {
                    transaction.show(currentFragment)
                }
                else {
                    transaction.add(R.id.container, currentFragment, currentTapTag)
                }

                imageViewNotification.setImageResource(R.mipmap.ti_notification_selected)
            }
            PROFILE_TAB -> {
                if (currentFragment == null) {
                    currentFragment = MaterialDesignBottomBarExampleFragment.newInstance("profile")
                }

                if (currentFragment.isAdded) {
                    transaction.show(currentFragment)
                }
                else {
                    transaction.add(R.id.container, currentFragment, currentTapTag)
                }

                imageViewProfile.setImageResource(R.mipmap.ti_profile_selected)
            }
        }

        transaction.commitAllowingStateLoss()
    }


    private fun resetTabBarStyle() {
        imageViewHome.setImageResource(R.mipmap.ti_home_un_selected)
        imageViewDiscover.setImageResource(R.mipmap.ti_discover_un_selected)
        imageViewNotification.setImageResource(R.mipmap.ti_notification_un_selected)
        imageViewProfile.setImageResource(R.mipmap.ti_profile_un_selected)
    }
}