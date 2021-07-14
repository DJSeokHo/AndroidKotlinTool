package com.swein.androidkotlintool.main.examples.infinityautoscrollpaddingbanner

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.swein.androidkotlintool.R

class InfinityAutoScrollPaddingBannerExampleActivity : AppCompatActivity() {

    private val imageList = mutableListOf<Int>()
    private lateinit var adapter: ViewPager2Adapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var textView: TextView

    private val viewPager2OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        var currentIndex = -1
        val runnable = Runnable {
            viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)
        }

        override fun onPageSelected(position: Int) {
//            ILog.debug("???", "position $position")
            currentIndex = position
            // must remove before postDelayed, remember !!
            viewPager2.removeCallbacks(runnable)
            viewPager2.postDelayed(runnable, 2500)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)

            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (currentIndex == 0) {
                    viewPager2.setCurrentItem(imageList.size - 2, false)
                }
                else if (currentIndex == imageList.size - 1) {
                    viewPager2.setCurrentItem(1, false)
                }

                // index from 0 to imageList.size - 1
                textView.text = "index is ${currentIndex - 1}"

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infinity_auto_scroll_padding_banner_example)

        findView()
        initViewPager2()
    }

    private fun findView() {
        viewPager2 = findViewById(R.id.viewPager2)
        textView = findViewById(R.id.textView)
    }

    private fun initViewPager2() {

        imageList.clear()

        imageList.add(R.mipmap.banner3)

        imageList.add(R.mipmap.banner1)
        imageList.add(R.mipmap.banner2)
        imageList.add(R.mipmap.banner3)

        imageList.add(R.mipmap.banner1)


        adapter = ViewPager2Adapter()

        viewPager2.adapter = adapter
        viewPager2.clipChildren = false
        viewPager2.clipToPadding = false
        viewPager2.offscreenPageLimit = 3

//        val paddingEnd = getScreenWidthPx(viewPager2.context) - dpToPx(viewPager2.context, 300f)
        // or
        val paddingEnd = (getScreenWidthPx(viewPager2.context) * 0.2).toInt()

        viewPager2.setPadding(0, 0, paddingEnd, 0)

        adapter.reload(imageList)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->

        }
        viewPager2.setPageTransformer(compositePageTransformer)

        viewPager2.registerOnPageChangeCallback(viewPager2OnPageChangeCallback)

        viewPager2.currentItem = 1
    }

    private fun getScreenWidthPx(context: Context): Int {
        val outMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = context.display
            display?.getRealMetrics(outMetrics)
        }
        else {
            @Suppress("DEPRECATION")
            val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
        }

        return outMetrics.widthPixels
    }

    private fun dpToPx(context: Context, dpValue: Float): Int {
        return (dpValue * (context.resources.displayMetrics.density) + 0.5f).toInt()
    }

    override fun onDestroy() {
        viewPager2.unregisterOnPageChangeCallback(viewPager2OnPageChangeCallback)
        super.onDestroy()
    }
}