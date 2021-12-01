package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.rootfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.BackStackAbleFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.multiplebackstackmanager.FragmentInfo

class ScrollViewRootFragment(fragmentInfo: FragmentInfo) :
    BackStackAbleFragment(fragmentInfo) {

    companion object {

        const val TAG = "ScrollViewRootFragment"

        @JvmStatic
        fun newInstance(fragmentInfo: FragmentInfo) =
            ScrollViewRootFragment(fragmentInfo).apply {
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ILog.debug("???", "onCreateView scroll view fragment")
        return inflater.inflate(
            R.layout.fragment_scroll_view_root,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ILog.debug("???", "onViewCreated scroll view fragment")

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

    override fun onDestroyView() {
        ILog.debug("???", "onDestroyView scroll view fragment")
        super.onDestroyView()
    }
}