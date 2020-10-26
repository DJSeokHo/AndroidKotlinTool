package com.swein.androidkotlintool.framework.util.endecode

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Base64
import com.google.android.gms.common.util.Base64Utils
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLEncoder
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class EnDeCodeUtil {

    companion object {

        @SuppressLint("GetInstance")
        fun encrypt(input: String, password: String): String {
            //1. 创建cipher对象
            val cipher = Cipher.getInstance("AES")
            //2. 初始化cipher
            //自己指定的秘钥
            val keySpec = SecretKeySpec(password.toByteArray(),"AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            //3. 加密和解密
            val encrypt = cipher.doFinal(input.toByteArray());
            return Base64Utils.encode(encrypt)
        }

        @SuppressLint("GetInstance")
        fun decrypt(input: String, password: String): String {
            //1. 创建cipher对象
            val cipher = Cipher.getInstance("AES")
            //2. 初始化cipher
            //自己指定的秘钥
            val keySpec = SecretKeySpec(password.toByteArray(),"AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            //3. 加密和解密
            val decrypt = cipher.doFinal(Base64Utils.decode(input))
            return String(decrypt)
        }

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