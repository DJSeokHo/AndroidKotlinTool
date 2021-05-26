package com.swein.androidkotlintool.main.examples.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.livedata.viewmodel.NameViewModel

class LiveDataDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_demo)

        lifecycle.addObserver(LiveDataDemoLCObserver())

    }
}