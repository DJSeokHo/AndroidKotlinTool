package com.swein.androidkotlintool.main.examples.customintentexample

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.swein.androidkotlintool.R

class CustomIntentExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_intent_example)

        findViewById<Button>(R.id.buttonLaunch).setOnClickListener {

            // launch from app with customize intent
            Intent("com.swein.intentreceiverexample.mainactivity.launchfromoutside").apply {
                putExtra("bundle", Bundle().apply {
                    putString("value1", "coding")
                    putString("value2", "with")
                    putString("value3", "cat")
                })
                startActivity(this)
            }

        }

        findViewById<Button>(R.id.buttonLaunchWithUri).setOnClickListener {

            // launch from app with uri
            Intent(Intent.ACTION_VIEW, Uri.parse("openapp://codingwithcat:8080/test?value1=coding&value2=with&value3=cat")).apply {
                startActivity(this)
            }

            /*
                launch from web if you need
                // js
                window.location = "openapp://codingwithcat:8080/test?value1=coding&value2=with&value3=cat"

                // html
                <a href="openapp://codingwithcat:8080/test?value1=coding&value2=with&value3=cat">open in app</a>
             */
        }

    }
}