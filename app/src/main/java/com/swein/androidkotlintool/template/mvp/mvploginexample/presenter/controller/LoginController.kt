package com.swein.androidkotlintool.template.mvp.mvploginexample.presenter.controller

import com.swein.androidkotlintool.framework.utility.thread.ThreadUtility

object LoginController {

    interface LoginControllerDelegate {
        fun onSuccess(response: String)
        fun onFailed()
    }

    fun requestLogin(id: String, password: String, delegate: LoginControllerDelegate) {
        // send id and password to your server
        // and waiting response

        ThreadUtility.startThread {

            // start your post or get
            Thread.sleep(3000)

            // you can do this part with okhttp or any other http open source

            // get response
            delegate.onSuccess("Any JSON object string from server response")
        }
    }
}