package com.swein.androidkotlintool.template.viewpagerfragment.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerFragmentAdapter: FragmentPagerAdapter {

    private var list: MutableList<Fragment> = mutableListOf()

    constructor(fragmentManager: FragmentManager): super(fragmentManager) {}

    fun setFragmentList(list: MutableList<Fragment>) {
        this.list.clear()
        this.list.addAll(list)
    }

    override fun getItem(p0: Int): Fragment {
        return list[p0]
    }

    override fun getCount(): Int {
        return list.size
    }
}