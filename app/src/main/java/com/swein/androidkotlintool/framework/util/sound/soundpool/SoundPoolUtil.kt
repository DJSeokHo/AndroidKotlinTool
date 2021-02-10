package com.swein.androidkotlintool.framework.util.sound.soundpool

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

object SoundPoolUtil {

    private lateinit var soundPool: SoundPool
    private lateinit var audioAttributes: AudioAttributes
    private var soundId = 0

    fun initSound(context: Context, resourceId: Int) {
        audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()

        soundPool = SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()

        soundId = soundPool.load(context, resourceId, 1)
    }

    fun play() {

        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)

    }

    fun release() {
        soundPool.release()
    }
}