package com.swein.androidkotlintool.main.examples.materialdesigntutorial.daysix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.swein.androidkotlintool.R

class MDDaySixActivity : AppCompatActivity() {

    private val textInputLayoutID: TextInputLayout by lazy {
        findViewById(R.id.textInputLayoutID)
    }

    private val textInputEditTextID: TextInputEditText by lazy {
        findViewById(R.id.textInputEditTextID)
    }

    private val textInputEditTextPassword: TextInputEditText by lazy {
        findViewById(R.id.textInputEditTextPassword)
    }

    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mdday_six)

        button.setOnClickListener {
            check()
        }

        textInputEditTextID.addTextChangedListener {

            val id = it.toString()

            if (id == "") {
                return@addTextChangedListener
            }

            when {
                id.length < 6 -> {
                    textInputLayoutID.error = "too short"
                }
                id.length > 8 -> {
                    textInputLayoutID.error = "too long"
                }
                else -> {
                    textInputLayoutID.error = null
                }
            }
        }

    }

    private fun check() {

        val id = textInputEditTextID.text?.trim().toString()
        val password = textInputEditTextPassword.text?.trim().toString()

        if (id.length < 6 || id.length > 8 || password.length != 10) {
            Toast.makeText(this, "check id and password", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "sign in", Toast.LENGTH_SHORT).show()
    }
}