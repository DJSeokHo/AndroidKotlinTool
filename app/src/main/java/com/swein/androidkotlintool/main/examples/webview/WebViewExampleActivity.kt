package com.swein.androidkotlintool.main.examples.webview

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.utility.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.thread.ThreadUtility
import com.swein.androidkotlintool.framework.utility.toast.ToastUtility
import com.swein.androidkotlintool.main.examples.webview.javascript.PaymentInterface
import com.swein.androidkotlintool.main.examples.webview.javascript.PersonalCertificationJsInterface
import java.net.URISyntaxException


class WebViewExampleActivity : AppCompatActivity() {

    private val frameLayoutContainer: FrameLayout by lazy {
        findViewById(R.id.container)
    }

    private val webViewHolder: WebViewHolder by lazy {
        WebViewHolder(this)
    }

    private val isCert = false
    private val isPayment = false

    private val findID = false
    private val findPW = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_example)

        frameLayoutContainer.addView(webViewHolder)
        webViewHolder.setDelegate(
            onPageFinished = { url ->
                val success = true

                if (url.contains("reservationId=") && url.contains("success=")) {
                    ILog.debug("???", "yes ??")
                    val parameters: List<String> = url.split("[?]")
                    ILog.debug("???", parameters[0] + " " + parameters[1])
                    val results = parameters[1].split("&").toTypedArray()
                    ILog.debug("???", results[0] + " " + results[1])

//                    for(String result : results) {
//                        if(result.contains("success")) {
//                            String[] isSuccess = result.split("=");
//                            ILog.iLogDebug(TAG, isSuccess[0] + " " + isSuccess[1]);
//                            if (isSuccess[1].equals("true")) {
//                                // success
//                                ILog.iLogDebug(TAG, "success");
//                                EventCenter.instance.sendEvent(ESSArrows.PAYMENT_CARD_SUCCESS, this, null);
//                                finish();
//                            }
//                            else {
//                                ILog.iLogDebug(TAG, "failed");
//                                loadPaymentUrl();
//                                success = false;
//                            }
//                        }
//                        else if(result.contains("message")) {
//                            String[] messages = result.split("=");
//                            if(messages.length > 1) {
//                                try {
//                                    ToastUtil.showCustomLongToastNormal(PaymentCardActivity.this, URLDecoder.decode(messages[1], "UTF-8"));
//                                }
//                                catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                    ToastUtil.showCustomLongToastNormal(PaymentCardActivity.this, messages[1]);
//                                }
//                            }
//                        }
//                    }
                    if (!success) {
                        finish()
                    }
                }
            },
            onLoadUrlAndOverrideCondition = { url ->
                ILog.debug("???", url)

                try {
                    ILog.debug("???", "try")
                    var intent: Intent
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        ILog.debug("???", "intent.getScheme = " + intent.scheme)
                        ILog.debug("???", "intent.getDataString = " + intent.dataString)
                    } catch (ex: URISyntaxException) {
                        ILog.debug("???", "Bad URI $url ${ex.message}")
                        return@setDelegate false
                    }

                    if (url.startsWith("intent")) {
                        ILog.debug("???", "intent ??")
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            intent?.let { startActivity(it) }
                        }
                        catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }
                        catch (e: ActivityNotFoundException) {
                            val packageName = intent.getPackage()
                            if (packageName != "") {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=$packageName")
                                    )
                                )
                            }
                        }
                        return@setDelegate true
                    } else if (url.startsWith("https://play.google.com/store/apps/details?id=") || url.startsWith(
                            "market://details?id="
                        )
                    ) {
                        ILog.debug("???", "google ??")
                        val uri: Uri = Uri.parse(url)
                        val packageName = uri.getQueryParameter("id")
                        if (packageName != null && packageName != "") {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=$packageName")
                                )
                            )
                        }
                        return@setDelegate true
                    }
                } catch (e: ActivityNotFoundException) {
                    ILog.debug("???", "error ====> " + e.message)
                    e.printStackTrace()
                    return@setDelegate false
                }

                webViewHolder.loadUrl(url)
                return@setDelegate true
            },
            onError = {
                ILog.debug("???", it)
            },
            onConsoleMessageDebugDescription = {
                ILog.debug("???", it)
            }
        )

        if (isCert) {
            webViewHolder.setupWebViewJavascript(PersonalCertificationJsInterface(object :
                PersonalCertificationJsInterface.JsInterfaceDelegate {

                override fun certificationSuccess(
                    birthdate: String,
                    mobileno: String,
                    name: String,
                    gender: String
                ) {
                    ILog.debug("???", "$birthdate $mobileno $name $gender")
                    ThreadUtility.startUIThread(0) {
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap["birthDate"] = birthdate
                        hashMap["phone"] = mobileno
                        hashMap["name"] = name
                        hashMap["gender"] = gender
                        hashMap["FIND_ID"] = findID
                        hashMap["FIND_PW"] = findPW
                        EventCenter.sendEvent(ESSArrows.PHONE_CERT_SUCCESS, this, hashMap)
                        finish()
                    }
                }

                override fun certificationFailed(errorMessage: String) {
                    ILog.debug("???", errorMessage)
                    ToastUtility.showCustomShortToastNormal(this@WebViewExampleActivity, errorMessage)
                }

            }), "certInterface")
        }

        if (isPayment) {
            webViewHolder.setupWebViewJavascript(PaymentInterface(object :
                PaymentInterface.PaymentInterfaceDelegate {
                override fun paymentResult(success: String) {
                    if (success == "Y") {
                        ILog.debug("???", "결제 성공")
                        EventCenter.sendEvent(ESSArrows.PAYMENT_SUCCESS, this, null)
                    } else {
                        ILog.debug("???", "결제 실패")
                        EventCenter.sendEvent(ESSArrows.PAYMENT_FAIL, this, null)
                    }
                    finish()
                }

                override fun startPayment(result: String) {
                    ILog.debug("???", "함수 호출됩니다.")
                }

            }), "paymentInterface")
        }

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