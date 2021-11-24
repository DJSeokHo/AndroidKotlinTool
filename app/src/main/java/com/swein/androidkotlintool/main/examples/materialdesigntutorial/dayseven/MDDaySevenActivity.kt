package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.display.DisplayUtility
import com.swein.androidkotlintool.framework.util.log.ILog
import kotlin.math.abs


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

    private val appBarLayout: AppBarLayout  by lazy {
        findViewById(R.id.appBarLayout)
    }

    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar)
    }

    private val viewCover: View by lazy {
        findViewById(R.id.viewCover)
    }

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingActionButton)
    }

    private val collapsingToolbarLayout: CollapsingToolbarLayout by lazy {
        findViewById(R.id.collapsingToolbarLayout)
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

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                appBarLayout, verticalOffset ->

            val offset = abs(verticalOffset)
            val scrollRange = appBarLayout.totalScrollRange

            var alpha = offset.toFloat() / scrollRange.toFloat()

            if (alpha < 0.3) {
                alpha = 0.3f
            }

            viewCover.alpha = alpha
            ILog.debug("???", "$offset $scrollRange $alpha")
        })

        floatingActionButton.setOnClickListener {
            Toast.makeText(this@MDDaySevenActivity, "floating action button click", Toast.LENGTH_SHORT).show()
        }

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#00000000"))
        toolbar.title = "title"
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE)
        collapsingToolbarLayout.setExpandedTitleMargin(
            DisplayUtility.dipToPx(this, 30f),
            0,
            0,
            DisplayUtility.dipToPx(this, 100f),
        )

        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
        toolbar.inflateMenu(R.menu.toolbar_menu)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this@MDDaySevenActivity, "navigation click", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.menuOne -> {
                    Toast.makeText(this@MDDaySevenActivity, "menu one", Toast.LENGTH_SHORT).show()
                }

                R.id.menuTwo -> {
                    Toast.makeText(this@MDDaySevenActivity, "menu two", Toast.LENGTH_SHORT).show()
                }

                R.id.menuThree -> {
                    Toast.makeText(this@MDDaySevenActivity, "menu three", Toast.LENGTH_SHORT).show()
                }
            }

            true
        }

        initData()
        initView()
    }

    private fun initData() {
        for (i in 0 until 10) {
            tabTitleList.add("title $i")
            tabSubTitleList.add("sub title $i")
            fragmentList.add(MDDaySevenFragment.newInstance())
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
        return 3
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