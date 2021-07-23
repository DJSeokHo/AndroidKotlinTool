package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment

import androidx.fragment.app.Fragment

open class RootBackStackAbleFragment(private val rootFragmentInfo: RootFragmentInfo): Fragment() {

    fun getFragmentInfo(): RootFragmentInfo {
        return rootFragmentInfo
    }

}