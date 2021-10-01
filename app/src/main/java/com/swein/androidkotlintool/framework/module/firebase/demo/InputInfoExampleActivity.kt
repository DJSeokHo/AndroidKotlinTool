package com.swein.androidkotlintool.framework.module.firebase.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.swein.androidkotlintool.R

class InputInfoExampleActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val textViewNickname: TextView by lazy {
        findViewById(R.id.textViewNickname)
    }
    private val textViewEmail: TextView by lazy {
        findViewById(R.id.textViewEmail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_info_example)
    }
}