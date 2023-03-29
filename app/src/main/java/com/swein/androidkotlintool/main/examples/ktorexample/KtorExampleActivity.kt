package com.swein.androidkotlintool.main.examples.ktorexample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.ktorexample.KtorExampleConstants.MY_TEST_SERVER_DOMAIN
import com.swein.androidkotlintool.main.examples.ktorexample.KtorExampleConstants.SAMPLE_IMAGE_FILE_PATH
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class KtorExampleActivity : AppCompatActivity() {

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ktor_example)

        KtorWrapper.onLog = {
            ILog.debug("???", it)
        }

        findViewById<Button>(R.id.buttonGet).setOnClickListener {

            lifecycleScope.launch {

                showProgress()

                try {
                    coroutineScope {

                        val request = async(Dispatchers.IO) {
                            KtorWrapper.get(
                                "$MY_TEST_SERVER_DOMAIN/get_test/member/token",
                                queryParameterMap = mutableMapOf<String, String>().apply {
                                    put("id", "shtest")
                                    put("password", "qwer1234")
                                }
                            )
                        }

                        val response = request.await()
                        ILog.debug("??? response", response.bodyAsText())

                        val jsonObject = JSONObject(response.bodyAsText())
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            val token = jsonObject.getString("value")

                            val infoRequest = async(Dispatchers.IO) {
                                KtorWrapper.get(
                                    "$MY_TEST_SERVER_DOMAIN/get_test/member/search/token",
                                    headerMap = mutableMapOf<String, String>().apply {
                                        put("X-AUTH-TOKEN", token)
                                    }
                                )
                            }

                            val infoResponse = infoRequest.await()
                            ILog.debug("??? infoResponse", infoResponse.bodyAsText())
                            hideProgress()
                        }

                    }
                }
                catch (e: Exception) {
                    ILog.debug("!!!!!eeeee", e.message)
                    Toast.makeText(this@KtorExampleActivity, e.message, Toast.LENGTH_SHORT).show()
                    hideProgress()
                }
            }

        }

        findViewById<Button>(R.id.buttonPost).setOnClickListener {

            lifecycleScope.launch {

                showProgress()

                try {
                    coroutineScope {

                        val request = async(Dispatchers.IO) {

                            val list = mutableListOf<FormDataFile>().also {
                                it.add(FormDataFile("file", SAMPLE_IMAGE_FILE_PATH, "ktor_test_image.jpg"))
                            }

                            KtorWrapper.post(
                                "$MY_TEST_SERVER_DOMAIN/post_test/member/create/form_data",
                                formDataMap = mutableMapOf<String, String>().apply {
                                    put("id", "ktortest")
                                    put("password", "qwer1234")
                                    put("nickname", "ktor test")
                                    put("email", "ktortest@test.com")
                                },
                                formDataFileList = list
                            )
                        }

                        val response = request.await()
                        ILog.debug("??? response", response.bodyAsText())
                        hideProgress()
                    }
                }
                catch (e: Exception) {
                    ILog.debug("!!!!!eeeee", e.message)
                    Toast.makeText(this@KtorExampleActivity, e.message, Toast.LENGTH_SHORT).show()
                    hideProgress()
                }
            }
        }

    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        // if you need
        KtorWrapper.releaseClient()
    }
}
