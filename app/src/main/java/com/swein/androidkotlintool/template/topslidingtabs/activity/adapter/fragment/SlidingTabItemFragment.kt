package com.swein.androidkotlintool.template.topslidingtabs.activity.adapter.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog

class SlidingTabItemFragment : Fragment() {

    companion object {
        private const val TAG = "SlidingTabItemFragment"
    }

    private var textViewTitle: TextView? = null

    private var rootView: View? = null
    private var text: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sliding_tab_item, container, false)
        initView()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateView()
    }

    private fun initView() {
        textViewTitle = rootView?.findViewById(R.id.textViewTitle)
    }

    fun setText(text: String) {
      this.text = text
    }

    private fun updateView() {
        textViewTitle?.text = text
    }

}
