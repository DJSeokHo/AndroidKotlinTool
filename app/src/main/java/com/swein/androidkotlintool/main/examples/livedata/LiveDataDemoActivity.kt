package com.swein.androidkotlintool.main.examples.livedata

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.livedata.viewmodel.ScoreLiveDataViewModel

class LiveDataDemoActivity : AppCompatActivity() {

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }
    private val textViewUser: TextView by lazy {
        findViewById(R.id.textViewUser)
    }
    private val textViewUserList: TextView by lazy {
        findViewById(R.id.textViewUserList)
    }
    private val button: Button by lazy {
        findViewById(R.id.button)
    }
    private val buttonFragment: Button by lazy {
        findViewById(R.id.buttonFragment)
    }

    private val viewModel: ScoreLiveDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_demo)

        initDataObserver()
        setListener()
    }

    private fun initDataObserver() {

        viewModel.currentScore.observe(this) { score ->
            textView.text = score.toString()
        }

        viewModel.currentUser.observe(this) { userScoreInfo ->
            val text = "${userScoreInfo.name} ${userScoreInfo.info}"
            textViewUser.text = text
        }

        viewModel.currentUserList.observe(this) { list ->
            textViewUserList.text = list.size.toString()
        }
    }

    private fun setListener() {
        button.setOnClickListener {

            viewModel.setValue(viewModel.getValue() + 1)
            viewModel.setUser("123", "okokok")
        }

        buttonFragment.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container, LiveDataDemoFragment.newInstance(), "fragment").commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {

        supportFragmentManager.findFragmentByTag("fragment")?.let {
            supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
            return
        }

        finish()
    }
}