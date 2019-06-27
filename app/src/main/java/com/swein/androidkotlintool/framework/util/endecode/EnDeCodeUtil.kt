package com.swein.androidkotlintool.framework.util.endecode

import android.text.TextUtils
import android.util.Base64
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLEncoder

class EnDeCodeUtil {

    companion object {

        fun chineseStringEncodeToUtf8(string: String): String {
            return URLEncoder.encode(string, "UTF-8")
        }

        fun fileToBase64(path: String): String? {
            if (TextUtils.isEmpty(path)) {
                return null
            }

            var inputStream: InputStream? = null
            val data: ByteArray
            var result: String? = null

            try {
                inputStream = FileInputStream(path)

                data = ByteArray(inputStream.available())

                inputStream.read(data)

                result = Base64.encodeToString(data, Base64.DEFAULT)
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
            finally {
                if (inputStream != null) {

                    try {
                        inputStream.close()
                    }
                    catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return result
        }

    }

}