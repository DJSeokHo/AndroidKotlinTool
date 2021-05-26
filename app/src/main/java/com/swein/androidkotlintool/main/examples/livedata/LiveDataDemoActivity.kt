package com.swein.androidkotlintool.main.examples.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.livedata.viewmodel.ScoreLiveDataViewModel
import com.swein.androidkotlintool.main.examples.viewmodel.ScoreViewModel

class LiveDataDemoActivity : AppCompatActivity() {

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }
    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    private val viewModel: ScoreLiveDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_demo)

        lifecycle.addObserver(LiveDataDemoLCObserver())

        val scoreObserver = Observer<Int> {  score ->
            textView.text = score.toString()
        }

        viewModel.currentScore.observe(this, scoreObserver)

        button.setOnClickListener {

            viewModel.setValue(viewModel.getValue() + 1)
        }

        viewModel.setValue(0)
    }
}