@file:Suppress("unused")

package com.architects.pokearch.core.data.di

import com.architects.pokearch.core.data.datasource.MediaPlayerDataSource
import com.architects.pokearch.core.data.mediaplayer.AndroidMediaPlayerSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindsMediaPlayerDataSource(
        androidMediaPlayerSource: AndroidMediaPlayerSource
    ): MediaPlayerDataSource

}
