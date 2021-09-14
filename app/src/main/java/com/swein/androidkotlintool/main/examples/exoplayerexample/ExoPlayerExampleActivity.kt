package com.swein.androidkotlintool.main.examples.exoplayerexample

import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.swein.androidkotlintool.R

class ExoPlayerExampleActivity : AppCompatActivity() {

    private lateinit var exoPlayerView: PlayerView

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource

    private lateinit var urlType: URLType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player_example)

        // don't forget
        // <uses-permission android:name="android.permission.WAKE_LOCK" />
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        findView()
        initPlayer()
    }

    private fun findView() {
        exoPlayerView = findViewById(R.id.exoPlayerView)
    }

    private fun initPlayer() {
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()

        simpleExoPlayer.addListener(playerListener)

        exoPlayerView.player = simpleExoPlayer

        exoPlayerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        createMediaSource()

        // Prepare the player with the source.
        simpleExoPlayer.setMediaSource(mediaSource)
        simpleExoPlayer.prepare()
    }

    private fun createMediaSource() {

//        urlType = URLType.MP4
//        urlType.url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"

        urlType = URLType.HLS
        urlType.url = "https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8"

        Log.d("???", "createMediaSource ${urlType.name} ${urlType.url}")

        simpleExoPlayer.seekTo(0)

        when (urlType) {
            URLType.MP4 -> {
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    this,
                    Util.getUserAgent(this, applicationInfo.name)
                )
                mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(Uri.parse(urlType.url)))
            }
            URLType.HLS -> {
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    this,
                    Util.getUserAgent(this, applicationInfo.name)
                )
                mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(
                        Uri.parse(urlType.url)))
            }
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onResume() {
        super.onResume()

        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.play()
    }

    override fun onPause() {
        super.onPause()

        simpleExoPlayer.pause()
        simpleExoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()

        simpleExoPlayer.pause()
        simpleExoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()

        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        simpleExoPlayer.stop()
        simpleExoPlayer.clearMediaItems()
    }

    private var playerListener = object : Player.Listener {

        // video
        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()

            if (urlType == URLType.HLS) {
                exoPlayerView.useController = false
            }

            if (urlType == URLType.MP4) {
                exoPlayerView.useController = true
            }

        }

        /* if you need
        override fun onSurfaceSizeChanged(width: Int, height: Int) {
            super.onSurfaceSizeChanged(width, height)
        }

        override fun onVideoSizeChanged(videoSize: VideoSize) {
            super.onVideoSizeChanged(videoSize)
        } */
        // video

        // player
        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Toast.makeText(this@ExoPlayerExampleActivity, "${error.message}", Toast.LENGTH_SHORT).show()
        }

        /* if you need
        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            super.onPlayWhenReadyChanged(playWhenReady, reason)
        }*/

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)

            if(!simpleExoPlayer.playWhenReady) {
                return
            }

            // do what you want to do after player ready

            /*
                playbackState:

                ExoPlayer.STATE_IDLE
                The player has been instantiated, but has not yet been prepared.

                ExoPlayer.STATE_BUFFERING
                The player is not able to play from the current position because not enough data has been buffered.

                ExoPlayer.STATE_READY
                The player is able to immediately play from the current position.
                This means the player will start playing media automatically if the player's playWhenReady property is true.
                If it is false, the player is paused.

                ExoPlayer.STATE_ENDED
                The player has finished playing the media.
             */

        }

        /* if you need
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            super.onIsLoadingChanged(isLoading)
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
        }*/
        // player

        // audio
        /* if you need
        override fun onAudioSessionIdChanged(audioSessionId: Int) {
            super.onAudioSessionIdChanged(audioSessionId)
        }

        override fun onAudioAttributesChanged(audioAttributes: AudioAttributes) {
            super.onAudioAttributesChanged(audioAttributes)
        }

        override fun onVolumeChanged(volume: Float) {
            super.onVolumeChanged(volume)
        }

        override fun onDeviceVolumeChanged(volume: Int, muted: Boolean) {
            super.onDeviceVolumeChanged(volume, muted)
        } */
        // audio
    }
}

enum class URLType(var url: String) {
    MP4(""), HLS("")
}