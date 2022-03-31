package com.swein.androidkotlintool.main.examples.googleadmobexample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog


class AdmobRewardedExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AdmobRewardedExampleActivity"
    }

    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob_rewarded_example)

        button.setOnClickListener {

            showRewardedAd()

        }
    }

    private fun showRewardedAd() {

        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                ILog.debug(TAG, adError.message)

                showRewardedAd()
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                ILog.debug(TAG, "Ad was loaded.")

                rewardedAd.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        ILog.debug(TAG, "Ad was shown.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        // Called when ad fails to show.
                        ILog.debug(TAG, "Ad failed to show.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        ILog.debug(TAG, "Ad was dismissed.")

                    }
                }

                rewardedAd.show(this@AdmobRewardedExampleActivity) { rewardItem ->

                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type
                    ILog.debug(TAG, "User earned the reward. $rewardAmount $rewardType")

                }

            }
        })

    }
}