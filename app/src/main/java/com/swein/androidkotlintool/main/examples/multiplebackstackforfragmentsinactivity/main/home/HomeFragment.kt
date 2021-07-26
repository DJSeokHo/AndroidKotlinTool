package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.BackStackAbleFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.FragmentInfo


class HomeFragment(fragmentInfo: FragmentInfo) :
    BackStackAbleFragment(fragmentInfo) {

    companion object {

        const val TAG = "HomeFragment"

        @JvmStatic
        fun newInstance(fragmentInfo: FragmentInfo) =
            HomeFragment(fragmentInfo).apply {
                arguments = Bundle().apply {

                }
            }
    }

    private lateinit var buttonRed: Button
    private lateinit var buttonGreen: Button
    private lateinit var buttonBlue: Button

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
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonRed = view.findViewById(R.id.buttonRed)
        buttonGreen = view.findViewById(R.id.buttonGreen)
        buttonBlue = view.findViewById(R.id.buttonBlue)

        buttonRed.setOnClickListener {

        }
        buttonGreen.setOnClickListener {

        }
        buttonBlue.setOnClickListener {

        }
    }

}