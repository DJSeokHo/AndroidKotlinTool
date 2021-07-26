package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.discover.DiscoverFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.favorite.FavoriteFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.home.HomeFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.profile.ProfileFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.FragmentInfo
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.MultipleBackStackManager

class MultipleBackStackExampleActivity : FragmentActivity() {

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigationView)
    }

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingActionButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_back_stack_example)

        setListener()

        bottomNavigationView.selectedItemId = R.id.menuHome
    }

    private fun setListener() {
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {

                    MultipleBackStackManager.createRootFragment(this, HomeFragment.newInstance(
                        FragmentInfo(HomeFragment.TAG, "menuHome")
                    ), R.id.frameLayoutContainer)

                    return@setOnItemSelectedListener true
                }
                R.id.menuDiscover -> {

                    MultipleBackStackManager.createRootFragment(this, DiscoverFragment.newInstance(
                        FragmentInfo(DiscoverFragment.TAG, "menuDiscover")
                    ), R.id.frameLayoutContainer)

                    return@setOnItemSelectedListener true
                }
                R.id.menuFavorite -> {

                    MultipleBackStackManager.createRootFragment(this, FavoriteFragment.newInstance(
                        FragmentInfo(FavoriteFragment.TAG, "menuFavorite")
                    ), R.id.frameLayoutContainer)

                    return@setOnItemSelectedListener true
                }
                R.id.menuProfile -> {

                    MultipleBackStackManager.createRootFragment(this, ProfileFragment.newInstance(
                        FragmentInfo(ProfileFragment.TAG, "menuProfile")
                    ), R.id.frameLayoutContainer)

                    return@setOnItemSelectedListener true
                }
            }

            false
        }

        floatingActionButton.setOnClickListener {

        }
    }

    override fun onBackPressed() {
        MultipleBackStackManager.pressBack(this) { actionTag: String, isRoot: Boolean ->
            if (isRoot) {
                when (actionTag) {
                    "menuHome" -> {
                        bottomNavigationView.selectedItemId = R.id.menuHome
                    }
                    "menuDiscover" -> {
                        bottomNavigationView.selectedItemId = R.id.menuDiscover
                    }
                    "menuFavorite" -> {
                        bottomNavigationView.selectedItemId = R.id.menuFavorite
                    }
                    "menuProfile" -> {
                        bottomNavigationView.selectedItemId = R.id.menuProfile
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        MultipleBackStackManager.clear()
        super.onDestroy()
    }
}