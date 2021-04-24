package com.swein.androidkotlintool.main.examples.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.databinding.ActivityDataBindingExampleBinding
import com.swein.androidkotlintool.databinding.ActivityViewBindingExampleBinding
import com.swein.androidkotlintool.framework.util.log.ILog

class DataBindingExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DataBindingExampleActivity"
    }

    private lateinit var binding: ActivityDataBindingExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBindingExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_data_binding_example)

        binding.exampleData = ExampleDataBean(11, "haha", "hehe")

        binding.button.setOnClickListener {
            ILog.debug(TAG, "click")

            binding.exampleData = ExampleDataBean(12, "haha12", "hehe12")

        }
    }
}