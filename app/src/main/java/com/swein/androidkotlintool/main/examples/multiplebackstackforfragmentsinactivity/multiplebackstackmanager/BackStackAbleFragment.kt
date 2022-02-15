package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.swein.androidkotlintool.main.examples.multiplebackstack.profile.MBSProfileFragment

abstract class BackStackAbleFragment(private val fragmentInfo: FragmentInfo): Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() =
            MBSProfileFragment().apply {
                arguments = Bundle().apply {
                    
                }
            }
    }

    fun getFragmentInfo(): FragmentInfo {
        return fragmentInfo
    }

    fun setAsRootFragment(containerIdInRootFragment: Int) {
        fragmentInfo.containerInFragment = containerIdInRootFragment
    }
}