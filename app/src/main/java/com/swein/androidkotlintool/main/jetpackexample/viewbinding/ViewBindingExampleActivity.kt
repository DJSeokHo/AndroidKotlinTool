package com.swein.androidkotlintool.main.jetpackexample.viewbinding

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.databinding.ActivityViewBindingExampleBinding
import com.swein.androidkotlintool.framework.utility.activity.ActivityUtil
import com.swein.androidkotlintool.framework.utility.debug.ILog

class ViewBindingExampleActivity : FragmentActivity() {

    companion object {
        private const val TAG = "ViewBindingExampleActivity"
    }

    private lateinit var binding: ActivityViewBindingExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBindingExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityUtil.addFragmentWithTAG(this, binding.frameLayoutContainer.id,
            ViewBindingExampleFragment.newInstance(), "", false, null)

        binding.textView.text = "123"
        binding.button.text = "haha"

        binding.button.setOnClickListener {
            ILog.debug(TAG, "click")
        }
    }
}