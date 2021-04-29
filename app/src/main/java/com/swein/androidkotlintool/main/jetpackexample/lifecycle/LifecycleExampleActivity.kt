package com.swein.androidkotlintool.main.jetpackexample.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.swein.androidkotlintool.R

class LifecycleExampleActivity : AppCompatActivity() {

    private lateinit var lcObserver: LCObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_example)

        lcObserver = LCObserver(this, lifecycle)

        lifecycle.addObserver(lcObserver)

        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // do something
        }
    }

}