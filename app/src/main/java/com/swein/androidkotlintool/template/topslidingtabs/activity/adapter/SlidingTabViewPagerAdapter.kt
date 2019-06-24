package com.swein.androidkotlintool.template.topslidingtabs.activity.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.template.topslidingtabs.activity.adapter.fragment.SlidingTabItemFragment

class SlidingTabViewPagerAdapter: FragmentPagerAdapter {

    companion object {
        private const val TAG = "SlidingTabViewPagerAdapter"
    }

    private var list: MutableList<Fragment>? = null

    constructor(fragmentManager: FragmentManager, list: MutableList<Fragment>) : super(fragmentManager) {
        this.list = list
    }

    override fun getItem(p0: Int): Fragment {
        return list?.get(p0)!!
    }

    override fun getCount(): Int {
        return list?.size!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab Title [$position]"
    }

}