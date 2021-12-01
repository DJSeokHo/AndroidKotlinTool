package com.swein.androidkotlintool.main.examples.youtubeplayer

import android.os.Bundle
import com.google.android.youtube.player.*
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class YoutubePlayerExampleActivity : YouTubeBaseActivity() {

    companion object {
        private const val TAG = "YoutubePlayerExampleActivity"
    }

    private val youTubePlayerView: YouTubePlayerView by lazy {
        findViewById(R.id.youTubePlayerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_player_example)

        /*
        https://img.youtube.com/vi/Ee2y5YoLQB4/mqdefault.jpg
        https://img.youtube.com/vi/Ee2y5YoLQB4/hqdefault.jpg
        https://img.youtube.com/vi/Ee2y5YoLQB4/maxresdefault.jpg
         */
        youTubePlayerView.initialize("AIzaSyAVx79aGFmshEL4dCg_0WmRdWVi3kANz7Y", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                youTubePlayer: YouTubePlayer?,
                p2: Boolean
            ) {
                ILog.debug(TAG, "onInitializationSuccess $p2")

                youTubePlayer?.loadVideo("Ee2y5YoLQB4")
                youTubePlayer?.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                ILog.debug(TAG, "onInitializationFailure $p1")

            }

        })

    }
}