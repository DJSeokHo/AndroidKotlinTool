package com.swein.androidkotlintool.framework.utility.uuid

import java.util.*

class UUIDUtil {
    companion object {
        fun getUUIDString(): String {
            return UUID.randomUUID().toString().replace("-", "")
        }
    }
}