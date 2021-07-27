package com.swein.androidkotlintool.main.examples.webview.javascript

import android.webkit.JavascriptInterface

class PaymentInterface(private var paymentInterfaceDelegate: PaymentInterfaceDelegate) {

    interface PaymentInterfaceDelegate {
        fun paymentResult(success : String)
        fun startPayment(result: String)
    }

    @JavascriptInterface
    fun paymentResult(success: String) {
        paymentInterfaceDelegate.paymentResult(success)
    }

    @JavascriptInterface
    fun startPayment(result: String) {
        paymentInterfaceDelegate.startPayment(result)
    }

}