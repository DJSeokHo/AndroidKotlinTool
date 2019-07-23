package com.swein.androidkotlintool.template.topcustomslidingtablayout.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.swein.androidkotlintool.R

class SubTabLayoutFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_sub_tab_layout, container, false)
            // findView()
        }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (rootView != null) {
            (rootView.parent as ViewGroup).removeView(rootView)
        }
    }

}
