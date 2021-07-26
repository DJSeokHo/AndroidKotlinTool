package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.BackStackAbleFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.FragmentInfo

class ProfileFragment(fragmentInfo: FragmentInfo): BackStackAbleFragment(fragmentInfo) {

    companion object {

        const val TAG = "ProfileFragment"

        @JvmStatic
        fun newInstance(fragmentInfo: FragmentInfo) =
            ProfileFragment(fragmentInfo).apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        setAsRootFragment(R.id.container)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile2, container, false)
    }
}