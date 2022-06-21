package com.swein.androidkotlintool.main.examples.googleadmobexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class AdmobInterstitialExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AdmobInterstitialActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob_interstitial_example)

        findViewById<Button>(R.id.button).setOnClickListener {

            InterstitialAd.load(
                this,"ca-app-pub-3940256099942544/1033173712",
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        ILog.debug(TAG, adError.message)
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        ILog.debug(TAG, "Ad was loaded.")

                        interstitialAd.fullScreenContentCallback = object: FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                ILog.debug(TAG, "Ad was dismissed.")
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                ILog.debug(TAG, "Ad failed to show.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                ILog.debug(TAG, "Ad showed fullscreen content.")
                            }
                        }

                        interstitialAd.show(this@AdmobInterstitialExampleActivity)
                    }
                })

        }

    }
}