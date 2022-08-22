package com.swein.androidkotlintool.framework.module.location.geo

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import java.util.*

class SHGeoCoder {

    private var geocoder: Geocoder

    constructor(context: Context, locale: Locale) {
        geocoder = Geocoder(context, locale)
    }

    constructor(context: Context) {
        geocoder = Geocoder(context)
    }

    fun getFromLocation(latitude: Double, longitude: Double, maxResult: Int): MutableList<Address> {

        val list = mutableListOf<Address>()

        if (Build.VERSION.SDK_INT >= 33) {
            geocoder.getFromLocation(latitude, longitude, maxResult) {
                list.addAll(it)
            }
        }
        else {
            geocoder.getFromLocation(latitude, longitude, maxResult)?.let {
                list.addAll(it)
            }
        }

        return list
    }
}