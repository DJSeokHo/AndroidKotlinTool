package com.swein.androidkotlintool.framework.module.location.geo

import android.content.Context
import android.location.Address
import android.location.Geocoder
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
        return geocoder.getFromLocation(latitude, longitude, maxResult)
    }
}