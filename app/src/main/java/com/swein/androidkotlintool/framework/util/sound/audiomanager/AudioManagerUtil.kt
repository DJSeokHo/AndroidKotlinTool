package com.swein.androidkotlintool.framework.util.sound.audiomanager

import android.content.Context
import android.media.AudioManager

object AudioManagerUtil {

    private lateinit var audioManager: AudioManager
    private var maxVolume = 0
    private var currentVolume = 0

    private var step = -1

    fun init(context: Context) {

        audioManager = context.getSystemService(android.content.Context.AUDIO_SERVICE) as AudioManager

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        for(i in 0 until maxVolume) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0)
        }
    }

    fun setNoMute() {
        for(i in 0 until maxVolume - currentVolume) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0)
        }
    }

    fun setNoMute(step: Int) {
        for(i in 0 until step) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0)
        }
    }

    fun resetAfterPlay() {
        for(i in 0 until maxVolume - currentVolume) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0)
        }
    }

    fun resetAfterPlay(step: Int) {
        for(i in 0 until step) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0)
        }
    }
    
    fun getMaxVolume(): Int {
        return maxVolume
    }

}