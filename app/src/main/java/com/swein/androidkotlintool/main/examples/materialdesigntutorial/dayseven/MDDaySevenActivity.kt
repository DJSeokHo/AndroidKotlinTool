package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog


class MDDaySevenActivity : FragmentActivity() {

    private val fragmentList: MutableList<Fragment> = mutableListOf()

    private val tabTitleList: MutableList<String> = mutableListOf()
    private val tabSubTitleList: MutableList<String> = mutableListOf()

    private lateinit var tabLayoutMediator: TabLayoutMediator

    private val tabLayout: TabLayout by lazy {
        findViewById(R.id.tabLayout)
    }

    private val viewPager2: ViewPager2 by lazy {
        findViewById(R.id.viewPager2)
    }

    private val viewPager2OnPageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            ILog.debug("???", "$position")
            val tabCount = tabLayout.tabCount
            for (i in 0 until tabCount) {
                tabLayout.getTabAt(i)?.let {
                    (it.customView as CustomTabViewHolder).setUnSelected()
                }
            }

            tabLayout.getTabAt(position)?.let {
                (it.customView as CustomTabViewHolder).setSelected()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mdday_seven)

        initData()
        initView()
    }

    private fun initData() {
        for (i in 0 until 10) {
            tabTitleList.add("title $i")
            tabSubTitleList.add("sub title $i")
            fragmentList.add(TempFragment())
        }
    }

    private fun initView() {

        viewPager2.offscreenPageLimit = 3
        viewPager2.adapter = TabLayoutAdapter(this, fragmentList)

        viewPager2.registerOnPageChangeCallback(viewPager2OnPageChangeCallback)

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2
        ) { tab, position ->

            val customTabViewHolder = CustomTabViewHolder(this)

            if (position == 0 || position == 5) {
                // create a tab include image when position at 0 or 5
                customTabViewHolder.setImageResource(R.drawable.camera_album)
                customTabViewHolder.setSubTitle("subtitle $position")
            }
            else {
                customTabViewHolder.setTitle("subtitle $position")
                customTabViewHolder.setSubTitle("subtitle $position")
            }

            tab.customView = customTabViewHolder
        }

        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        viewPager2.unregisterOnPageChangeCallback(viewPager2OnPageChangeCallback)
        super.onDestroy()
    }
}

class TabLayoutAdapter(fragmentActivity: FragmentActivity, private val fragmentList: MutableList<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 10
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}

class TempFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_temp, container, false)
    }
}