package com.swein.androidkotlintool.main.examples.unityadsexample

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize

class UnityAdsExampleActivity : AppCompatActivity() {

    companion object {
        private const val UNITY_GAME_ID = "4296271"
        private const val INTERSTITIAL_ID = "Interstitial_Android"
        private const val REWARDED_ID = "Rewarded_Android"
        private const val BANNER_ID = "Banner_Android"
        private const val TAG = "UnityAdsExampleActivity"
    }

    private var isTestMode = true
    private var isFullScreenAdShown = false

    private var bannerView: BannerView? = null
    private var isBannerAdShown = false

    private val frameLayoutAdContainer: FrameLayout by lazy {
        findViewById(R.id.frameLayoutAdContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unity_ads_example)

        UnityAds.initialize(applicationContext, UNITY_GAME_ID, isTestMode)

        UnityAds.addListener(object : IUnityAdsListener {
            override fun onUnityAdsReady(p0: String?) {
                // p0 is ad's ID
                ILog.debug(TAG, "onUnityAdsReady $p0")

                // show ad when activity start
                when (p0) {
                    INTERSTITIAL_ID -> {
                        // interstitial ad
//                        showAd(INTERSTITIAL_ID)
                    }

                    REWARDED_ID -> {
                        // rewarded ad
//                        showAd(REWARDED_ID)
                    }

                    BANNER_ID -> {
//                        showAd(BANNER_ID)
                    }
                }

            }

            override fun onUnityAdsStart(p0: String?) {
                ILog.debug(TAG, "onUnityAdsStart $p0")
            }

            override fun onUnityAdsFinish(p0: String?, p1: UnityAds.FinishState?) {
                ILog.debug(TAG, "onUnityAdsFinish $p0")

                when (p1) {
                    UnityAds.FinishState.COMPLETED -> {
                        // Reward the user for watching the ad to completion.
                        Toast.makeText(this@UnityAdsExampleActivity, "completed", Toast.LENGTH_SHORT).show()
                    }
                    UnityAds.FinishState.SKIPPED -> {
                        // Do not reward the user for skipping the ad.
                        Toast.makeText(this@UnityAdsExampleActivity, "skipped", Toast.LENGTH_SHORT).show()
                    }
                    UnityAds.FinishState.ERROR -> {
                        // Log an error.
                        Toast.makeText(this@UnityAdsExampleActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onUnityAdsError(p0: UnityAds.UnityAdsError?, p1: String?) {
                ILog.debug(TAG, "onUnityAdsError $p0")
            }

        })

        setListener()

    }

    private fun setListener() {

        findViewById<Button>(R.id.buttonInterstitial).setOnClickListener {
            isFullScreenAdShown = false
            showAd(INTERSTITIAL_ID)
        }

        findViewById<Button>(R.id.buttonRewarded).setOnClickListener {
            isFullScreenAdShown = false
            showAd(REWARDED_ID)
        }

        findViewById<Button>(R.id.buttonBanner).setOnClickListener {
            showAd(BANNER_ID)
        }


        findViewById<TextView>(R.id.textViewCloseBanner).setOnClickListener {

            bannerView?.let {
                frameLayoutAdContainer.removeView(it)
                frameLayoutAdContainer.visibility = View.GONE
                bannerView = null
                isBannerAdShown = false
            }
        }
    }

    private fun showAd(adID: String) {

        if (INTERSTITIAL_ID == adID|| REWARDED_ID == adID ) {
            // interstitial ad can skip, rewarded ad can not skip

            if (isFullScreenAdShown) {
                return
            }

            isFullScreenAdShown = true

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
        else if (BANNER_ID == adID) {

            if (isBannerAdShown) {
                return
            }

            isBannerAdShown = true

            bannerView = BannerView(this, adID, UnityBannerSize(320, 50))
            bannerView!!.listener = object : BannerView.IListener {
                override fun onBannerLoaded(p0: BannerView?) {
                    ILog.debug(TAG, "onBannerLoaded")
                }

                override fun onBannerClick(p0: BannerView?) {
                    ILog.debug(TAG, "onBannerClick")
                }

                override fun onBannerFailedToLoad(p0: BannerView?, p1: BannerErrorInfo?) {
                    ILog.debug(TAG, "onBannerFailedToLoad $p1")
                }

                override fun onBannerLeftApplication(p0: BannerView?) {
                    ILog.debug(TAG, "onBannerLeftApplication")
                }

            }
            bannerView!!.load()
            // add to 0, means insert banner behind the textViewCloseBanner
            frameLayoutAdContainer.addView(bannerView!!, 0)
            frameLayoutAdContainer.visibility = View.VISIBLE
        }

    }

}