package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager

import androidx.fragment.app.Fragment

abstract class BackStackAbleFragment(private val fragmentInfo: FragmentInfo): Fragment() {

    fun getFragmentInfo(): FragmentInfo {
        return fragmentInfo
    }

    fun setAsRootFragment(containerIdInRootFragment: Int) {
        fragmentInfo.containerInFragment = containerIdInRootFragment
    }
}