package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager

import androidx.fragment.app.Fragment

open class BackStackAbleFragment(private val fragmentInfo: FragmentInfo): Fragment() {

    fun getFragmentInfo(): FragmentInfo {
        return fragmentInfo
    }
}