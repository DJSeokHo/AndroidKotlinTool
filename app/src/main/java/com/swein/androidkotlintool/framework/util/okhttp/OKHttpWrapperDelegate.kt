package com.swein.androidkotlintool.framework.util.okhttp

import okhttp3.Call
import okhttp3.Response
import java.io.IOException

interface OKHttpWrapperDelegate {
    fun onFailure(call: Call, e: IOException)
    fun onResponse(call: Call, response: Response)
}