package com.swein.androidkotlintool.main.jetpackexample.navigation.sign.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swein.androidkotlintool.R

class SignInFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(test: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString("test", test)
                }
            }
    }

    private var test = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            test = it.getString("test", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }


}