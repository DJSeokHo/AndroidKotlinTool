package com.swein.androidkotlintool.main.examples.onbackpresseddispatcherexample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.R


class OnBackPressedDispatcherExampleFragment : Fragment() {

    companion object {

        fun add(activity: AppCompatActivity, index: Int) {

            activity.supportFragmentManager.beginTransaction().let {
                it.add(
                    R.id.container,
                    newInstance(index = index),
                    "fragment_$index"
                )
                it.addToBackStack(null)
                it.commitAllowingStateLoss()
            }

        }

        fun remove(activity: FragmentActivity) {
            activity.supportFragmentManager.popBackStackImmediate()
        }

        @JvmStatic
        private fun newInstance(index: Int) =
            OnBackPressedDispatcherExampleFragment().apply {
                arguments = Bundle().apply {
                    putInt("index", index)
                }
            }
    }

    private lateinit var textView: TextView
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            index = it.getInt("index", 0)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("???", "Fragment onBackPressed")
                remove(requireActivity())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_on_back_pressed_dispatcher_example,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById(R.id.textView)

        textView.text = "Fragment $index"
    }

}