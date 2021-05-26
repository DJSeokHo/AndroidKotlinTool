package com.swein.androidkotlintool.main.examples.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.swein.androidkotlintool.R

class TeamAFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() =
            TeamAFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private val viewModel: ScoreViewModel by activityViewModels()

    private lateinit var rootView: View
    private val textView: TextView by lazy {
        rootView.findViewById(R.id.textView)
    }
    private val button: Button by lazy {
        rootView.findViewById(R.id.button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_team_a, container, false)

        button.setOnClickListener {
            viewModel.scoreTeamA = viewModel.scoreTeamA + 1
            updateView()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateView()
    }

    private fun updateView() {
        textView.text = viewModel.scoreTeamA.toString()
    }
}