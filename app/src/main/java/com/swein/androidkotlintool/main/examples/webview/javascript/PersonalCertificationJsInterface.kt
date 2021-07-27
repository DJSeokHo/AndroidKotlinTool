package com.swein.androidkotlintool.main.examples.webview.javascript

import android.webkit.JavascriptInterface

class PersonalCertificationJsInterface(private val jsInterfaceDelegate: JsInterfaceDelegate) {
    interface JsInterfaceDelegate {
        fun certificationSuccess(
            birthdate: String,
            mobileno: String,
            name: String,
            gender: String
        )

        fun certificationFailed(errorMessage: String)
    }

    /**
     *
     * @param birthdate
     * @param mobileno
     * @param name
     * @param gender 0: 여성 1: 남성
     */
    @JavascriptInterface
    fun certificationSuccess(
        birthdate: String,
        mobileno: String,
        name: String,
        gender: String
    ) {
        jsInterfaceDelegate.certificationSuccess(birthdate, mobileno, name, gender)
    }

    @JavascriptInterface
    fun certificationFailed(errorMessage: String) {
        jsInterfaceDelegate.certificationFailed(errorMessage)
    }

}