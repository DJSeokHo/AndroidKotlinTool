package com.swein.androidkotlintool.template.viewpagerfragment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


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