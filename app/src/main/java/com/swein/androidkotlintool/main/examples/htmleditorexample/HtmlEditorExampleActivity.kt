package com.swein.androidkotlintool.main.examples.htmleditorexample


import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import com.shine56.richtextx.view.RichEditText
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.date.DateUtility
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.okhttp.FormDataFile
import com.swein.androidkotlintool.framework.util.okhttp.OKHttpWrapper
import com.swein.androidkotlintool.framework.util.okhttp.OKHttpWrapperDelegate
import com.swein.androidkotlintool.framework.util.push.PushUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtility
import com.swein.androidkotlintool.framework.util.toast.ToastUtility
import com.swein.androidkotlintool.main.examples.systemphotopicker.SystemPhotoPickManager
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class HtmlEditorExampleActivity : AppCompatActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private val imageUrlList = mutableListOf<String>()

    private val richEditText: RichEditText by lazy {
        findViewById(R.id.richEditText)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_html_editor_example)

        imageUrlList.clear()

        findViewById<Button>(R.id.buttonSave).setOnClickListener {

            ILog.debug("???", Html.toHtml(richEditText.text, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE))
            ILog.debug("???!!!", richEditText.text.toString().trim())

            var firstImageUrl = ""

            var textOnly = richEditText.text.toString().trim()
            for (imageUrl in imageUrlList) {
                if (textOnly.contains(imageUrl) && firstImageUrl == "") {
                    firstImageUrl = imageUrl
                }
                textOnly = textOnly.replace(imageUrl, "")
            }
            ILog.debug("???!!!", textOnly)
            ILog.debug("11111", firstImageUrl)

            send()
        }


        findViewById<Button>(R.id.button).setOnClickListener {

            systemPhotoPickManager.requestPermission {

//                it.selectPicture { uri ->
//                    SHGlide.setImage(imageView, uri = uri)
//                }

                it.selectPathPicture(true) { filePath ->
                    ILog.debug("???", filePath)

                    setImage(filePath)
                }

//                it.takePictureWithFilePath(true) { filePath ->
//                    SHGlide.setImage(imageView, filePath = filePath)
//                }

//            it.takePictureWithUri { uri ->
//                SHGlide.setImage(imageView, uri = uri)
//            }

//            it.takePictureWithBitmap { bitmap ->
//                SHGlide.setImage(imageView, bitmap = bitmap)
//            }

            }

        }

    }

    private fun setImage(filePath: String) {
        val formDataFloat = FormDataFile(filePath, "${DateUtility.getCurrentDateTimeSSSStringWithNoSpace("_")}.jpg")
        val list = mutableListOf<FormDataFile>()
        list.add(formDataFloat)
        OKHttpWrapper.requestPost(url = "https://www.onnoffcompany.com/v1/main/boardImage", fileList = list, fileKey = "files",
            okHttpWrapperDelegate = object : OKHttpWrapperDelegate {
            override fun onFailure(call: Call, e: IOException) {
                OKHttpWrapper.cancelCall(call)
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                try {
                    val responseString = OKHttpWrapper.getStringResponse(response)
                    ILog.debug("???", responseString)

                    val responseJSONObject = JSONObject(responseString)

                    val success = responseJSONObject.getBoolean("success")

                    if (success) {
                        var data = responseJSONObject.getString("data")

                        if (data != "") {
                            data = "https://www.onnoffcompany.com/v1/static/img/board/$data"
                        }
                        imageUrlList.add(data)

                        ThreadUtility.startUIThread(0) {

                            val image = richEditText
                                .imageBuilder
                                .setImageUrl(data)
                                .setDrawableGet {
                                    Drawable.createFromPath(filePath)
                                }
                                .create()
                            richEditText.insertPhoto(image)
                            ILog.debug("???", "set image: $filePath $data")

                        }
                    }

                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
                finally {
                    OKHttpWrapper.cancelCall(call)
                }
            }

        })
    }

    private fun send() {

        val header = mutableMapOf<String, String>()
        header["X-AUTH-TOKEN"] = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NjI2OCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2MzE4NTQyNTgsImV4cCI6MTY2MzM5MDI1OH0.kOecdsL4VULzTwUJbHyJz2P6QsxI6oxCwrjA_tw8i2U"

        val formData = mutableMapOf<String, String>()
        formData["title"] = "123123"
        formData["contents"] = Html.toHtml(richEditText.text, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)

        var firstImageUrl = ""
        var textOnly = richEditText.text.toString().trim()
        for (imageUrl in imageUrlList) {
            if (textOnly.contains(imageUrl) && firstImageUrl == "") {
                firstImageUrl = imageUrl
            }
            textOnly = textOnly.replace(imageUrl, "")
        }

        formData["textOnly"] = textOnly
        formData["imgFileName"] = firstImageUrl.replace("https://www.onnoffcompany.com/v1/static/img/board/", "")

        OKHttpWrapper.requestPost(url = "https://www.onnoffcompany.com/v1/board/boardFreeWrite",
            header = header, formData = formData,
            okHttpWrapperDelegate = object : OKHttpWrapperDelegate {
                override fun onFailure(call: Call, e: IOException) {
                    OKHttpWrapper.cancelCall(call)
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    try {
                        val responseString = OKHttpWrapper.getStringResponse(response)
                        ILog.debug("???", responseString)

//                        val responseJSONObject = JSONObject(responseString)
//
//                        val success = responseJSONObject.getBoolean("success")
//
//                        if (success) {
//                            val data = responseJSONObject.getString("data")
//
//                            imageUrlList.add(data)
//
//                            ThreadUtility.startUIThread(0) {
//
//                                val image = richEditText
//                                    .imageBuilder
//                                    .setImageUrl(data)
//                                    .setDrawableGet {
//                                        Drawable.createFromPath(filePath)
//                                    }
//                                    .create()
//                                richEditText.insertPhoto(image)
//                                ILog.debug("???", "set image: $filePath $data")
//
//                            }
//                        }

                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                    finally {
                        OKHttpWrapper.cancelCall(call)
                    }
                }

            })
    }

}