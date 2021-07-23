package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment.RootFragmentInfo
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment.RootFragmentBuilder
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.MultipleBackStackManager
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
                R.id.menuOne -> {

                    MultipleBackStackManager.createRootFragment(
                        RootFragmentBuilder.Builder()
                            .setActionTag("menuOne")
                            .setFragmentTag(ScrollViewRootFragment.TAG)
                            .setContextCallback {
                                this
                            }
                            .setContainerIdCallback {
                                R.id.frameLayoutContainer
                            }
                            .setFragmentCallback { fragmentTag, actionTag ->
                                ScrollViewRootFragment.newInstance(
                                    RootFragmentInfo(fragmentTag, actionTag)
                                )
                            }.build()
                    )

                    return@setOnItemSelectedListener true
                }
                R.id.menuTwo -> {

                    MultipleBackStackManager.createRootFragment(
                        RootFragmentBuilder.Builder()
                            .setActionTag("menuTwo")
                            .setFragmentTag(TextViewRootFragment.TAG)
                            .setContextCallback {
                                this
                            }
                            .setContainerIdCallback {
                                R.id.frameLayoutContainer
                            }
                            .setFragmentCallback { fragmentTag, actionTag ->
                                TextViewRootFragment.newInstance(RootFragmentInfo(fragmentTag, actionTag), "menuTwo")
                            }
                            .build()
                    )

                    return@setOnItemSelectedListener true
                }
                R.id.menuThree -> {

                    MultipleBackStackManager.createRootFragment(
                        RootFragmentBuilder.Builder()
                            .setActionTag("menuThree")
                            .setFragmentTag(TextViewRootFragment.TAG)
                            .setContextCallback {
                                this
                            }
                            .setContainerIdCallback {
                                R.id.frameLayoutContainer
                            }
                            .setFragmentCallback { fragmentTag, actionTag ->
                                TextViewRootFragment.newInstance(RootFragmentInfo(fragmentTag, actionTag), "menuThree")
                            }
                            .build()
                    )

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