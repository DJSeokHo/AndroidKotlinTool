package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.rootfragments.childfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.MultipleBackStackManager
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment.ChildBackStackAbleFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment.ChildFragmentInfo


class TextViewChildFragment(childFragmentInfo: ChildFragmentInfo): ChildBackStackAbleFragment(childFragmentInfo) {

    companion object {

        const val TAG = "TextViewChildFragment"

        @JvmStatic
        fun newInstance(childFragmentInfo: ChildFragmentInfo, content: String) =
            TextViewChildFragment(childFragmentInfo).apply {
                arguments = Bundle().apply {
                    this.putString("content", content)
                }
            }
    }

    private var content = ""

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            content = it.getString("content", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_view_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ILog.debug("???", "onViewCreated $content")

        textView = view.findViewById(R.id.textView)

        textView.setOnClickListener {

            MultipleBackStackManager.createChildFragmentOnCurrentRootFragment(
                newInstance(ChildFragmentInfo(TAG, "textSub"), "$content child"),
                R.id.container)

        }

        textView.text = content
    }

    override fun onDestroyView() {
        ILog.debug("???", "onDestroyView $content")
        super.onDestroyView()
    }


}