package com.swein.androidkotlintool.main.examples.customtablayoutexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.swein.androidkotlintool.R


class CustomTabLayoutExampleFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(index: Int) =
            CustomTabLayoutExampleFragment().apply {
                arguments = Bundle().apply {
                    putInt("index", index)
                }
            }
    }

    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            index = it.getInt("index", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_tab_layout_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.textView).text = "Fragment $index"

    }

}