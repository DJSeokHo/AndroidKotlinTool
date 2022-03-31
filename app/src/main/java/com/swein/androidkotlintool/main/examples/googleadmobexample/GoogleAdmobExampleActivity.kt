package com.swein.androidkotlintool.main.examples.googleadmobexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.swein.androidkotlintool.R


class GoogleAdmobExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_admob_example)

        MobileAds.initialize(this) {}

        findViewById<Button>(R.id.buttonBanner).setOnClickListener {

            Intent(this, AdmobBannerExampleActivity::class.java).also {
                startActivity(it)
            }

        }

        findViewById<Button>(R.id.buttonInterstitial).setOnClickListener {

            Intent(this, AdmobInterstitialExampleActivity::class.java).also {
                startActivity(it)
            }
        }

        findViewById<Button>(R.id.buttonReward).setOnClickListener {

            Intent(this, AdmobRewardedExampleActivity::class.java).also {
                startActivity(it)
            }
        }

        findViewById<Button>(R.id.buttonNative).setOnClickListener {

            Intent(this, AdmobNativeExampleActivity::class.java).also {
                startActivity(it)
            }
        }


    }


}