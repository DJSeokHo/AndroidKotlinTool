package com.swein.androidkotlintool.main.jetpackexample.navigation.sign.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.jetpackexample.navigation.topnavigationbar.CustomTopNavigationBarViewHolder

class SignInFragment : Fragment() {

    companion object {
        private const val TAG = "SignInFragment"
    }

    private lateinit var buttonRegister: Button
    private lateinit var buttonAuth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ILog.debug(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ILog.debug(TAG, "onCreateView")

        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_sign_in, container, false).apply {

            initTopNavigationBar(this)
            findView(this)
            setListener()

            return this
        }
    }

    private fun initTopNavigationBar(view: View) {
        CustomTopNavigationBarViewHolder(view.context, view.findViewById(R.id.frameLayoutTopNavigationBarContainer),
            onImageButtonStartClick = {

                if (!findNavController().popBackStack()) {
                    activity?.finish()
                }
            },
            onImageButtonEndClick = {

                if (!findNavController().popBackStack()) {
                    activity?.finish()
                }
            }
        ).apply {
            this.toggleEndClick(null, false)
            this.toggleStartClick()
            this.setTitle("Sign In")
        }
    }

    private fun findView(view: View) {
        buttonRegister = view.findViewById(R.id.buttonRegister)
        buttonAuth = view.findViewById(R.id.buttonAuth)
    }

    private fun setListener() {
        buttonRegister.setOnClickListener {

            val bundle = bundleOf("test" to "test")
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment, bundle)
        }

        buttonAuth.setOnClickListener {

            val action = SignInFragmentDirections.actionSignInFragmentToAuthFragment("test")
            findNavController().navigate(action)
        }

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