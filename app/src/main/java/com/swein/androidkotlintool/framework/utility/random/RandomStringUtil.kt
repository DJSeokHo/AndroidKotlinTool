package com.swein.androidkotlintool.framework.utility.random

class RandomStringUtil {
    companion object {

        fun createRandomString(length: Int): String {
            val ss = CharArray(length)
            var i = 0

            while (i < length) {

                when ((Math.random() * 3).toInt()) {
                    0 -> ss[i] = ('A'.toDouble() + Math.random() * 26).toChar()
                    1 -> ss[i] = ('a'.toDouble() + Math.random() * 26).toChar()
                    else -> ss[i] = ('0'.toDouble() + Math.random() * 10).toChar()
                }
                i += 1
            }
            return String(ss)
        }

    }
}