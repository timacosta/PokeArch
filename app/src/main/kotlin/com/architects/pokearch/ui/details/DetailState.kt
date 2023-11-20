package com.architects.pokearch.ui.details

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun rememberDetailState(
    context: Context = LocalContext.current,
    mediaPlayer: MediaPlayer = MediaPlayer()
): DetailState = remember(context, mediaPlayer) {
    DetailState(context, mediaPlayer)
}

class DetailState(
    private val context: Context,
    private val mediaPlayer: MediaPlayer
) {
    @Composable
    fun PlayCry(url: String){
        SideEffect {
            playCry(url)
        }
    }

    private fun playCry(url: String){
        try {
            mediaPlayer.apply {
                setAudioAttributes(getAudioAtrributes())
                setDataSource(context, url.toUri())
                prepare()
                start()
            }
        } catch (e: Exception){
            Log.e(this.javaClass.simpleName, e.message.toString())
        }
    }

    private fun getAudioAtrributes() =
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
}
