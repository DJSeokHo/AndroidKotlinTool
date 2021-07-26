package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment

import androidx.fragment.app.Fragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment.ChildBackStackAbleFragment

class RootBackStackAbleFragment(private val rootFragmentInfo: RootFragmentInfo): Fragment() {

    fun getRootFragmentInfo(): RootFragmentInfo {
        return rootFragmentInfo
    }
}