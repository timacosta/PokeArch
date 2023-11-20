package com.architects.pokearch.ui.details

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Composable
fun rememberDetailState(
    dispatcher: CoroutineDispatcher,
    context: Context = LocalContext.current,
    mediaPlayer: MediaPlayer = MediaPlayer(),
    once: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
): DetailState = remember(dispatcher, context, mediaPlayer, once) {
    DetailState(dispatcher, context, mediaPlayer, once)
}

class DetailState(
    private val dispatcher: CoroutineDispatcher,
    private val context: Context,
    private val mediaPlayer: MediaPlayer,
    private val once: MutableState<Boolean>
) {
    @Composable
    fun PlayCry(url: String){
        LaunchedEffect(Unit) {
            withContext(dispatcher){
                if (!once.value) {
                    playCry(url)
                    once.value = true
                }
            }
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
