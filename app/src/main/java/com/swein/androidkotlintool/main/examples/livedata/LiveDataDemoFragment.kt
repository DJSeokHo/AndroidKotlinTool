package com.swein.androidkotlintool.main.examples.livedata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.livedata.viewmodel.ScoreLiveDataViewModel


class LiveDataDemoFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = LiveDataDemoFragment()
    }

    private lateinit var rootView: View

    private val textView: TextView by lazy {
        rootView.findViewById(R.id.textView)
    }

    private val button: Button by lazy {
        rootView.findViewById(R.id.button)
    }

    private val viewModel: ScoreLiveDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_live_data_demo, container, false)
        initObserver()
        setListener()

        return rootView
    }

    private fun initObserver() {

        activity?.let {
            viewModel.currentScore.observe(it) { score ->
                textView.text = score.toString()
            }
        }
    }

    private fun setListener() {
        button.setOnClickListener {
            viewModel.setValue(viewModel.getValue() + 1)
        }
    }
}