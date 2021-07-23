package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment

import androidx.fragment.app.Fragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment.ChildBackStackAbleFragment

open class RootBackStackAbleFragment(private val rootFragmentInfo: RootFragmentInfo): Fragment() {

    fun getRootFragmentInfo(): RootFragmentInfo {
        return rootFragmentInfo
    }

    var onStartNewChildFragment: (() -> ChildBackStackAbleFragment)? = null

}