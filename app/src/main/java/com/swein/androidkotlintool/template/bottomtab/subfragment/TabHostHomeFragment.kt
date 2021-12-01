package com.swein.androidkotlintool.template.bottomtab.subfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.fragment.app.Fragment

import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.activity.ActivityUtil

class TabHostHomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_host_home, container, false)
    }

}
