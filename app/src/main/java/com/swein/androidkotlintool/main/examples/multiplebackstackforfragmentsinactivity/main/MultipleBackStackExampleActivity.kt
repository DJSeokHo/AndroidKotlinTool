package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.FragmentInfo
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.MultipleBackStackManager
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.rootfragments.ScrollViewRootFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.rootfragments.TextViewRootFragment

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

        bottomNavigationView.selectedItemId = R.id.menuOne
    }

    private fun setListener() {
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {

//                    MultipleBackStackManager.createRootFragment(this, ScrollViewRootFragment.newInstance(
//                        FragmentInfo(ScrollViewRootFragment.TAG, "menuOne")
//                    ), R.id.frameLayoutContainer)


                    return@setOnItemSelectedListener true
                }
                R.id.menuDiscover -> {

//                    MultipleBackStackManager.createRootFragment(this, TextViewRootFragment.newInstance(
//                        FragmentInfo(TextViewRootFragment.TAG, "menuTwo"), "menuTwo"
//                    ), R.id.frameLayoutContainer)

                    return@setOnItemSelectedListener true
                }
                R.id.menuNotification -> {

//                    MultipleBackStackManager.createRootFragment(this, TextViewRootFragment.newInstance(
//                        FragmentInfo(TextViewRootFragment.TAG, "menuThree"), "menuThree"
//                    ), R.id.frameLayoutContainer)

                    return@setOnItemSelectedListener true
                }
                R.id.menuProfile -> {

                    return@setOnItemSelectedListener true
                }
            }

            false
        }

        floatingActionButton.setOnClickListener {

        }
    }

    override fun onBackPressed() {
        MultipleBackStackManager.pressBack(this)
    }

    override fun onDestroy() {
        MultipleBackStackManager.clear()
        super.onDestroy()
    }
}