package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.discover.DiscoverFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.BackStackAbleFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.FragmentInfo

class FavoriteFragment(fragmentInfo: FragmentInfo): BackStackAbleFragment(fragmentInfo) {

    companion object {

        const val TAG = "FavoriteFragment"

        @JvmStatic
        fun newInstance(fragmentInfo: FragmentInfo) =
            FavoriteFragment(fragmentInfo).apply {
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
        return inflater.inflate(R.layout.fragment_favorite2, container, false)
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