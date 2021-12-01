package com.swein.androidkotlintool.main.jetpackexample.navigation.sign.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class SignUpFragment : Fragment() {

    companion object {

        private const val TAG = "SignUpFragment"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ILog.debug(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ILog.debug(TAG, "${arguments?.getString("test")}")
        ILog.debug(TAG, "onCreateView")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onDestroyView() {
        ILog.debug(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        ILog.debug(TAG, "onDestroy")
        super.onDestroy()
    }
}