package com.swein.androidkotlintool.main.examples.webview

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog

class WebViewExampleActivity : AppCompatActivity() {

    private val frameLayoutContainer: FrameLayout by lazy {
        findViewById(R.id.container)
    }

    private val webViewHolder: WebViewHolder by lazy {
        WebViewHolder(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_example)

        frameLayoutContainer.addView(webViewHolder)
        webViewHolder.setDelegate(
            onPageFinished = {
                ILog.debug("??", it)
            },
            onLoadUrlAndOverrideCondition = {
                webViewHolder.loadUrl(it)
                true
            },
            onError = {
                ILog.debug("??", it)
            },
            onConsoleMessageDebugDescription = {
                ILog.debug("??", it)
            }
        )
        webViewHolder.loadUrl("https://www.google.com")
    }

    override fun onBackPressed() {

        if (webViewHolder.closeWhenPressBack) {
            finish()
        }

        if (webViewHolder.canGoBack()) {
            webViewHolder.goBack()
            return
        }

        finish()
    }

    override fun onDestroy() {
        webViewHolder.destroyWebView()
        super.onDestroy()
    }
}