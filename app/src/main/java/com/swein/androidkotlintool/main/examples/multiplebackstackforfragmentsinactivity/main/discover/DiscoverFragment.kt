package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.BackStackAbleFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.FragmentInfo

class DiscoverFragment(fragmentInfo: FragmentInfo): BackStackAbleFragment(fragmentInfo) {

    companion object {

        const val TAG = "DiscoverFragment"

        @JvmStatic
        fun newInstance(fragmentInfo: FragmentInfo) =
            DiscoverFragment(fragmentInfo).apply {
                arguments = Bundle().apply {
                }
            }
    }

    private lateinit var button: Button

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
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = view.findViewById(R.id.button)

        button.setOnClickListener {

//            MultipleBackStackManager.createChildFragmentOnCurrentRootFragment(
//                TextViewChildFragment.newInstance(FragmentInfo(TextViewChildFragment.TAG, "textSub"), "$content child"))

        }

    }
}