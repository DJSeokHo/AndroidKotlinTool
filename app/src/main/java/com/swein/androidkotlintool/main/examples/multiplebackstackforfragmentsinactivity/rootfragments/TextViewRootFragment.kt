package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.rootfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.BackStackAbleFragment
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.FragmentInfo
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.MultipleBackStackManager
import com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.rootfragments.childfragments.TextViewChildFragment

class TextViewRootFragment(fragmentInfo: FragmentInfo): BackStackAbleFragment(fragmentInfo) {
    
    companion object {

        const val TAG = "TextViewRootFragment"

        @JvmStatic
        fun newInstance(fragmentInfo: FragmentInfo, content: String) =
            TextViewRootFragment(fragmentInfo).apply {
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
        ILog.debug("???", "onCreateView $content")
        return inflater.inflate(R.layout.fragment_text_view_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ILog.debug("???", "onViewCreated $content")

        textView = view.findViewById(R.id.textView)

        textView.setOnClickListener {

            MultipleBackStackManager.createChildFragmentOnCurrentRootFragment(
                TextViewChildFragment.newInstance(FragmentInfo(TextViewChildFragment.TAG, "textSub"), "$content child"))

        }

        textView.text = content
    }



    override fun onDestroyView() {
        ILog.debug("???", "onDestroyView $content")
        super.onDestroyView()
    }

}