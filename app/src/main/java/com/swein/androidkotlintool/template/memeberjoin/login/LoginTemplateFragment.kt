package com.swein.androidkotlintool.template.memeberjoin.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.swein.androidkotlintool.R


class LoginTemplateFragment : Fragment() {

    companion object {
        private const val TAG = "LoginTemplateFragment"
    }

    interface LoginTemplateFragmentDelegate {
        fun onJoin()
        fun onProcess()
        fun onSuccess()
        fun onFailed()
    }

    private var rootView: View? = null

    private var loginTemplateFragmentDelegate: LoginTemplateFragmentDelegate? = null

    private var editTextID: EditText? = null
    private var editTextPassword: EditText? = null
    private var buttonLogin: Button? = null
    private var buttonJoin: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login_template, container, false)

        findView()
        setListener()

        return rootView
    }

    fun setDelegate(loginTemplateFragmentDelegate: LoginTemplateFragmentDelegate) {
        this.loginTemplateFragmentDelegate = loginTemplateFragmentDelegate
    }

    private fun findView() {
        editTextID = rootView?.findViewById(R.id.editTextID)
        editTextPassword = rootView?.findViewById(R.id.editTextPassword)
        buttonLogin = rootView?.findViewById(R.id.buttonLogin)
        buttonJoin = rootView?.findViewById(R.id.buttonJoin)
    }

    private fun setListener() {

        buttonLogin?.let {
            it.setOnClickListener {
                if(!checkInputIsNull()) {

                }
            }
        }

        buttonJoin?.let {
            it.setOnClickListener {
                loginTemplateFragmentDelegate?.onJoin()
            }
        }
    }

    private fun checkInputIsNull(): Boolean {

        var isNull = true

        editTextID?.text?.trim()?.let {
            if( it.toString() != "") {
                isNull = false
            }
        }

        editTextPassword?.text?.trim()?.let {
            if( it.toString() != "") {
                isNull = false
            }
        }

        return isNull
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
