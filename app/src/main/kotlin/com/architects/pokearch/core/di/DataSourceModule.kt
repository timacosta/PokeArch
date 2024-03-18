@file:Suppress("unused")

package com.architects.pokearch.core.di

import com.architects.pokearch.core.framework.database.PokemonRoomDataSource
import com.architects.pokearch.core.framework.mediaplayer.AndroidMediaPlayerSource
import com.architects.pokearch.core.framework.network.PokemonServerDataSource
import com.architects.pokearch.core.framework.vibration.VibrationAndroid
import com.architects.pokearch.data.datasource.MediaPlayerDataSource
import com.architects.pokearch.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.data.datasource.VibrationSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindsMediaPlayerDataSource(
        androidMediaPlayerSource: AndroidMediaPlayerSource
    ): MediaPlayerDataSource

    @Binds
    abstract fun bindsPokemonLocalDataSource(
        pokemonLocalDataSource: PokemonRoomDataSource
    ): PokemonLocalDataSource

    @Binds
    abstract fun bindsPokemonRemoteDataSource(
        pokemonServiceDataSource: PokemonServerDataSource
    ): PokemonRemoteDataSource

    @Binds
    abstract fun bindsVibrationSource(
        vibrationSource: VibrationAndroid
    ): VibrationSource
}
