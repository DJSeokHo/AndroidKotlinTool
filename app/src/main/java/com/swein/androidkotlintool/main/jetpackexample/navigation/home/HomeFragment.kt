package com.swein.androidkotlintool.main.jetpackexample.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.jetpackexample.navigation.topnavigationbar.CustomTopNavigationBarViewHolder

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var buttonSign: Button
    private lateinit var textViewNumber: TextView
    private lateinit var buttonCount: Button

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ILog.debug(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ILog.debug(TAG, "onCreateView")

        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_home, container, false).apply {
            initTopNavigationBar(this)
            findView(this)
            setListener()
            return this
        }
    }

    private fun initTopNavigationBar(view: View) {
        CustomTopNavigationBarViewHolder(view.context, view.findViewById(R.id.frameLayoutTopNavigationBarContainer)).apply {
            this.toggleEndClick(null, false)
            this.toggleStartClick(null, false)
            this.setTitle("Home")
        }
    }

    private fun findView(view: View) {
        buttonSign = view.findViewById(R.id.buttonSign)
        textViewNumber = view.findViewById(R.id.textViewNumber)
        buttonCount = view.findViewById(R.id.buttonCount)
    }

    private fun setListener() {
        buttonSign.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSignNavigation()
            findNavController().navigate(action)
        }

        buttonCount.setOnClickListener {
            count ++
            textViewNumber.text = "$count"
        }
    }

    override fun onDestroyView() {
        ILog.debug(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        ILog.debug(TAG, "onDestroy")
        super.onDestroy()
    }
}