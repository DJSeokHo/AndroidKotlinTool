package com.swein.androidkotlintool.main.jetpackexample.navigation.sign.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.jetpackexample.navigation.topnavigationbar.CustomTopNavigationBarViewHolder

class AuthFragment : Fragment() {

    companion object {
        private const val TAG = "AuthFragment"
    }

    private lateinit var buttonRegister: Button

    val args: AuthFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ILog.debug(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ILog.debug(TAG, args.test)
        ILog.debug(TAG, "onCreateView")
        inflater.inflate(R.layout.fragment_auth, container, false).apply {
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
            this.setTitle("Auth")
        }
    }

    private fun findView(view: View) {
        buttonRegister = view.findViewById(R.id.buttonRegister)
    }

    private fun setListener() {
        buttonRegister.setOnClickListener {
            val bundle = bundleOf("test" to args.test)
            findNavController().navigate(R.id.action_authFragment_to_signUpFragment, bundle)
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