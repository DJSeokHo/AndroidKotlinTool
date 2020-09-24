package com.swein.androidkotlintool.template.viewpagerfragment.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.timer.TimerUtil
import com.swein.androidkotlintool.template.viewpagerfragment.adapter.ViewPagerFragmentAdapter
import com.swein.androidkotlintool.template.viewpagerfragment.adapter.item.ViewPagerItemFragment
import java.util.*

class ViewPagerFragment : Fragment() {

    companion object {
        private const val TAG = "ViewPagerFragment"
    }

    private var rootView: View? = null

    private var list: MutableList<Fragment>? = null
    private var viewPager: ViewPager? = null
    private var viewPagerFragmentAdapter: ViewPagerFragmentAdapter? = null

    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager, container, false)
        findView()
        initDate()
        return rootView
    }

    private fun initDate() {
        list = mutableListOf()

        list?.let {

            /**
             * header and end for
             * infinite scroll view pager
             */
            var viewPagerItemFragment: ViewPagerItemFragment?

            // header
            viewPagerItemFragment = ViewPagerItemFragment()
            viewPagerItemFragment.setText("Fragment 5")
            it.add(viewPagerItemFragment)

            for(index in 0..5) {
                viewPagerItemFragment = ViewPagerItemFragment()
                viewPagerItemFragment.setText("Fragment $index")
                it.add(viewPagerItemFragment)
            }

            // end
            viewPagerItemFragment = ViewPagerItemFragment()
            viewPagerItemFragment.setText("Fragment 0")
            it.add(viewPagerItemFragment)
        }

        viewPagerFragmentAdapter = ViewPagerFragmentAdapter(activity?.supportFragmentManager!!)
        viewPagerFragmentAdapter?.setFragmentList(list!!)

        viewPager?.adapter = viewPagerFragmentAdapter
        viewPager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {

            var currentPosition: Int? = null

            override fun onPageScrollStateChanged(p0: Int) {

                if (p0 != ViewPager.SCROLL_STATE_IDLE) {
                    return
                }

                if (0 == currentPosition) {
                    viewPager?.setCurrentItem(list?.size!! - 2, false)
                }
                else if (list?.size!! - 1 == currentPosition) {
                    viewPager?.setCurrentItem(1, false)
                }
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                currentPosition = p0
            }
        })

        viewPager?.currentItem = 1

        timer = TimerUtil.createTimerTask(3000, 4000, Runnable {

            val index = viewPager?.currentItem!! + 1
            viewPager?.currentItem = index

        })
    }

    private fun findView() {
        viewPager = rootView?.findViewById(R.id.viewPager)
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }

}
