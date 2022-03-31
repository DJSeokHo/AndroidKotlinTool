package com.swein.androidkotlintool.main.examples.googleadmobexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.*
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class AdmobBannerExampleActivity : AppCompatActivity() {

    private val adView: AdView by lazy {
        findViewById(R.id.adView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob_banner_example)

        loadAd(adView, "FULL_BANNER")
    }

    @SuppressLint("MissingPermission")
    private fun loadAd(adView: AdView, type: String) {

        adView.loadAd(AdRequest.Builder().build())

        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                ILog.debug(type, "onAdLoaded")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                ILog.debug(type, "onAdFailedToLoad: ${adError.message}")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                ILog.debug(type, "onAdOpened")
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                ILog.debug(type, "onAdClicked")
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.

                // only run when first closed
                ILog.debug(type, "onAdClosed")
            }
        }
    }
}