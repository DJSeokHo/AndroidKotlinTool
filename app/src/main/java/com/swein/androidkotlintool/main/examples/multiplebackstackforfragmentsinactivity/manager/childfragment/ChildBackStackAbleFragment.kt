package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment

import androidx.fragment.app.Fragment

open class ChildBackStackAbleFragment(private val childFragmentInfo: ChildFragmentInfo): Fragment() {

    fun getFragmentInfo(): ChildFragmentInfo {
        return childFragmentInfo
    }

}