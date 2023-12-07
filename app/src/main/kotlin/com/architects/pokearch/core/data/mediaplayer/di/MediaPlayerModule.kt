package com.architects.pokearch.core.data.mediaplayer.di

import android.media.MediaPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaPlayerModule {
    @Provides
    fun providesMediaPlayer(): MediaPlayer = MediaPlayer()
}
