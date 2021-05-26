package com.swein.androidkotlintool.main.examples.viewmodel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.R

class ViewModelDemoActivity : FragmentActivity() {

    private val buttonA: Button by lazy {
        findViewById(R.id.buttonA)
    }

    private val buttonB: Button by lazy {
        findViewById(R.id.buttonB)
    }

    private val textViewA: TextView by lazy {
        findViewById(R.id.textViewA)
    }

    private val textViewB: TextView by lazy {
        findViewById(R.id.textViewB)
    }

    private val buttonNew: Button by lazy {
        findViewById(R.id.buttonNew)
    }

    private val viewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model_demo)

        setListener()
    }

    override fun onResume() {
        super.onResume()
        updateView()
    }

    private fun setListener() {

        buttonA.setOnClickListener {
            viewModel.scoreTeamA = viewModel.scoreTeamA + 1
            updateView()
        }

        buttonB.setOnClickListener {
            viewModel.scoreTeamB = viewModel.scoreTeamB + 1
            updateView()
        }

        textViewA.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container, TeamAFragment.newInstance(), "A").commitAllowingStateLoss()
        }

        textViewB.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container, TeamBFragment.newInstance(), "B").commitAllowingStateLoss()
        }

        buttonNew.setOnClickListener {

            startActivity(Intent(this,  ViewModelNewTestActivity::class.java))

        }
    }

    private fun updateView() {
        textViewA.text = viewModel.scoreTeamA.toString()
        textViewB.text = viewModel.scoreTeamB.toString()
    }

    override fun onBackPressed() {

        supportFragmentManager.findFragmentByTag("A")?.let {
            supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
            return
        }

        supportFragmentManager.findFragmentByTag("B")?.let {
            supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
            return
        }

        finish()
    }
}