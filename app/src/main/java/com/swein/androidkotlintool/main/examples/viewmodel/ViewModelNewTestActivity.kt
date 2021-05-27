package com.swein.androidkotlintool.main.examples.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.swein.androidkotlintool.R

class ViewModelNewTestActivity : AppCompatActivity() {

//    private val viewModel: ScoreViewModel by viewModels()
    private val viewModel: ScoreAndroidViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(this.application)
            .create(ScoreAndroidViewModel::class.java)
    }

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }
    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model_new_test)

        button.setOnClickListener {
            viewModel.scoreTeamA = viewModel.scoreTeamA + 1
            updateView()
        }

    }

    override fun onResume() {
        super.onResume()

        updateView()
    }

    private fun updateView() {
        textView.text = viewModel.scoreTeamA.toString()
    }
}