package com.swein.androidkotlintool.main.examples.youtubeplayer

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog

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