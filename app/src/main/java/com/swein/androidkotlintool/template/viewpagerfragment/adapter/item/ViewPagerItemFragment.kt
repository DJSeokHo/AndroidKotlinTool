package com.swein.androidkotlintool.template.viewpagerfragment.adapter.item


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.swein.androidkotlintool.R


class ViewPagerItemFragment : Fragment() {

    private var rootView: View? = null
    private var textViewTitle: TextView? = null

    private var text: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager_item, container, false)
        findView()
        return rootView
    }

    private fun findView() {
        textViewTitle = rootView?.findViewById(R.id.textViewTitle)
    }

    fun setText(text: String) {
        this.text = text
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateView()
    }

    private fun updateView() {
        textViewTitle?.text = text
    }

}
