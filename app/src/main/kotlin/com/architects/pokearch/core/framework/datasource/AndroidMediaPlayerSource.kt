package com.architects.pokearch.core.framework.datasource

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import com.architects.pokearch.core.data.datasource.MediaPlayerDataSource
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
                setAudioAttributes(getAudioAtrributes())
                setDataSource(context, url.toUri())
                prepare()
                start()
            }
        } catch (e: Exception){
            Log.e(this.javaClass.simpleName, e.message.toString())
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    private fun getAudioAtrributes() =
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
}
