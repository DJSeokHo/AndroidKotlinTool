package com.swein.androidkotlintool.framework.utility.random

import java.util.*

class RandomNumberUtil {
    companion object {

        fun getRandomIntegerNumber(from: Int, nextFrom: Int): Int {

            val rand = Random()
            return rand.nextInt(from) + nextFrom
        }

    }
}