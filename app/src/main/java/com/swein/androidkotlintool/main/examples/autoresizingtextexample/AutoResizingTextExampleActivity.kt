package com.swein.androidkotlintool.main.examples.autoresizingtextexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.swein.androidkotlintool.R

class AutoResizingTextExampleActivity : AppCompatActivity() {

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    private val buttonShortText: Button by lazy {
        findViewById(R.id.buttonShortText)
    }

    private val buttonMiddleText: Button by lazy {
        findViewById(R.id.buttonMiddleText)
    }

    private val buttonLongText: Button by lazy {
        findViewById(R.id.buttonLongText)
    }

    private val buttonLongLongText: Button by lazy {
        findViewById(R.id.buttonLongLongText)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_resizing_text_example)

        buttonShortText.setOnClickListener {
            textView.text = "Coding with cat"
        }

        buttonMiddleText.setOnClickListener {
            textView.text = "Coding with cat is a youtube channel"
        }

        buttonLongText.setOnClickListener {
            textView.text = "Coding with cat is a youtube channel, please subscribe this channel to learn android"
        }

        buttonLongLongText.setOnClickListener {
            textView.text = "Coding with cat is a youtube channel, please subscribe this channel to learn Android development tutorial step by step"
        }
    }
}