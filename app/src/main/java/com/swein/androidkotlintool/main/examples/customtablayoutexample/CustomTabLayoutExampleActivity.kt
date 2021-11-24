package com.swein.androidkotlintool.main.examples.customtablayoutexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swein.androidkotlintool.R

class CustomTabLayoutExampleActivity : AppCompatActivity() {

    private val tabLayout1: TabLayout by lazy {
        findViewById(R.id.tabLayout1)
    }
    private val tabLayout2: TabLayout by lazy {
        findViewById(R.id.tabLayout2)
    }
    private val viewPager2: ViewPager2 by lazy {
        findViewById(R.id.viewPager2)
    }

    private val fragmentList: MutableList<Fragment> = mutableListOf()
    private val tabTitleList: MutableList<String> = mutableListOf()

    private lateinit var tabLayoutMediator1: TabLayoutMediator
    private lateinit var tabLayoutMediator2: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_tab_layout_example)

        initData()
        initView()
    }


    private fun initData() {

        tabTitleList.add("Coding")
        fragmentList.add(CustomTabLayoutExampleFragment.newInstance(0))

        tabTitleList.add("With")
        fragmentList.add(CustomTabLayoutExampleFragment.newInstance(1))

        tabTitleList.add("Cat")
        fragmentList.add(CustomTabLayoutExampleFragment.newInstance(2))

    }

    private fun initView() {

        viewPager2.offscreenPageLimit = 3
        viewPager2.adapter = TabLayoutAdapter(this, fragmentList)

        tabLayoutMediator1 = TabLayoutMediator(tabLayout1, viewPager2) { tab, position ->
            tab.text = tabTitleList[position]
        }

        tabLayoutMediator2 = TabLayoutMediator(tabLayout2, viewPager2) { tab, position ->
            tab.text = tabTitleList[position]
        }

        tabLayoutMediator1.attach()
        tabLayoutMediator2.attach()
    }

    class TabLayoutAdapter(fragmentActivity: FragmentActivity, private val fragmentList: MutableList<Fragment>) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }
}