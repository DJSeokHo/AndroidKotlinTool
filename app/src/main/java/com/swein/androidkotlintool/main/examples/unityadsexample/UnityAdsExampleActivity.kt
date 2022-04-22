package com.swein.androidkotlintool.main.examples.unityadsexample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds

class UnityAdsExampleActivity : AppCompatActivity() {

    companion object {
        private const val UNITY_GAME_ID = "4720361"
        private const val INTERSTITIAL_ID = "Interstitial_Android"
        private const val REWARDED_ID = "Rewarded_Android"
        private const val TAG = "UnityAdsExampleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unity_ads_example)

        UnityAds.initialize(this, UNITY_GAME_ID, false, object: IUnityAdsInitializationListener {
            override fun onInitializationComplete() {
                ILog.debug(TAG, "onInitializationComplete")
                setListener()
            }

            override fun onInitializationFailed(
                p0: UnityAds.UnityAdsInitializationError?,
                p1: String?
            ) {
                ILog.debug(TAG, "onInitializationFailed $p0 $p1")
            }

        })

    }

    private fun setListener() {

        findViewById<Button>(R.id.buttonInterstitial).setOnClickListener {
            loadAd(INTERSTITIAL_ID)
        }

        findViewById<Button>(R.id.buttonRewarded).setOnClickListener {
            loadAd(REWARDED_ID)
        }
    }

    private fun loadAd(adID: String) {

        UnityAds.load(adID, object : IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(p0: String?) {

                // show ad when activity start
                when (p0) {
                    INTERSTITIAL_ID -> {
                        // interstitial ad
                        showAd(INTERSTITIAL_ID)
                    }

                    REWARDED_ID -> {
                        // rewarded ad
                        showAd(REWARDED_ID)
                    }
                }

            }

            override fun onUnityAdsFailedToLoad(
                p0: String?,
                p1: UnityAds.UnityAdsLoadError?,
                p2: String?
            ) {
                ILog.debug(TAG, "onUnityAdsFailedToLoad $p0 $p1 $p2")
            }

        })
    }

    private fun showAd(adID: String) {

        UnityAds.show(this, adID, object : IUnityAdsShowListener {
            override fun onUnityAdsShowFailure(
                p0: String?,
                p1: UnityAds.UnityAdsShowError?,
                p2: String?
            ) {
                ILog.debug(TAG, "onUnityAdsShowFailure $p0 $p1 $p2")
            }

            override fun onUnityAdsShowStart(p0: String?) {
                ILog.debug(TAG, "onUnityAdsShowStart $p0")
            }

            override fun onUnityAdsShowClick(p0: String?) {
                ILog.debug(TAG, "onUnityAdsShowClick $p0")
            }

            override fun onUnityAdsShowComplete(
                p0: String?,
                p1: UnityAds.UnityAdsShowCompletionState?
            ) {
                ILog.debug(TAG, "onUnityAdsShowComplete $p0 $p1")
            }

        })


    }
}