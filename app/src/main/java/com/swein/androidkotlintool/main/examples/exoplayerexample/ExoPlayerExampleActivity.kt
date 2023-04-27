package com.swein.androidkotlintool.main.examples.exoplayerexample

import android.app.Activity
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.swein.androidkotlintool.R


class ExoPlayerExampleActivity : AppCompatActivity() {

    private lateinit var constraintLayoutRoot: ConstraintLayout
    private lateinit var styledPlayerView: StyledPlayerView

    private lateinit var exoPlayer: ExoPlayer

//    private lateinit var urlType: URLType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player_example)

        // don't forget
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

//        val rootView = window.decorView.rootView

        findView()
        initPlayer()

//        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, windowInsetsCompat: WindowInsetsCompat ->
//
//           windowInsetsCompat.consumeSystemWindowInsets()
//        }

    }

    private fun findView() {
        constraintLayoutRoot = findViewById(R.id.constraintLayoutRoot)
        styledPlayerView = findViewById(R.id.styledPlayerView)
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()

        exoPlayer.addListener(playerListener)

        styledPlayerView.player = exoPlayer

        // streaming video doesn't need a controller, mp4 video needs a controller
//        styledPlayerView.useController = true
//        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")))

        // HLS(Http Live Streaming) is a video transfer protocol, the suffix of the video is m3u8
        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8")))

        exoPlayer.prepare()
    }

//    private fun enableImmersiveMode(activity: Activity) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            activity.window.setDecorFitsSystemWindows(false)
//            val controller = activity.window.insetsController
//            controller?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//            controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
//        else {
//            activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//        }
//
//    }
//
//    private fun disableImmersiveMode(activity: Activity) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            activity.window.setDecorFitsSystemWindows(true)
//            val controller = activity.window.insetsController
//            controller?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_DEFAULT
//            }
//        }
//        else {
//            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//
//        }
//    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        val constraintSet = ConstraintSet()
//        constraintSet.connect(styledPlayerView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
//        constraintSet.connect(styledPlayerView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
//        constraintSet.connect(styledPlayerView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
//        constraintSet.connect(styledPlayerView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
//
//        constraintSet.applyTo(constraintLayoutRoot)
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//            enableImmersiveMode(this)
//
//        }
//        else {
//
//            disableImmersiveMode(this)
//
//            val layoutParams = styledPlayerView.layoutParams as ConstraintLayout.LayoutParams
//            layoutParams.dimensionRatio = "16:9"
//        }
//
//        window.decorView.requestLayout()
//    }

    override fun onResume() {
        super.onResume()

        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()

        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        exoPlayer.stop()
        exoPlayer.clearMediaItems()
    }

    private var playerListener = object : Player.Listener {

        // video
        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()

            // after the video was playing, you can do something here
            // for example, show a toast, or change UI, or make UI to full screen, or........

            Toast.makeText(this@ExoPlayerExampleActivity, "have fun with this video", Toast.LENGTH_SHORT).show()
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

            if(!exoPlayer.playWhenReady) {
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
