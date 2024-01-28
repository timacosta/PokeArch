package com.architects.pokearch.core.framework.mediaplayer

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import com.architects.pokearch.data.datasource.MediaPlayerDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidMediaPlayerSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaPlayer: MediaPlayer
): MediaPlayerDataSource {

    override suspend fun playCry(url: String) {
        mediaPlayCry(url)
    }

    private fun mediaPlayCry(url: String){
            try {
                mediaPlayer.apply {
                    reset()
                    setAudioAttributes(getAudioAtrributes())
                    setDataSource(context, url.toUri())
                    prepare()
                    start()
                }
            } catch (e: Exception){
                Log.e(this.javaClass.simpleName, e.stackTraceToString())
                mediaPlayer.release()
            }
    }

    private fun getAudioAtrributes() =
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
}
