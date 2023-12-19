@file:Suppress("unused")

package com.architects.pokearch.core.framework.di

import com.architects.pokearch.core.data.datasource.MediaPlayerDataSource
import com.architects.pokearch.core.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.core.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.core.framework.datasource.AndroidMediaPlayerSource
import com.architects.pokearch.core.framework.local.PokemonRoomDataSource
import com.architects.pokearch.core.framework.network.PokemonServerDataSource
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

    @Binds
    abstract fun bindsPokemonLocalDataSource(
        pokemonLocalDataSource: PokemonRoomDataSource
    ): PokemonLocalDataSource

    @Binds
    abstract fun bindsPokemonRemoteDataSource(
        pokemonServiceDataSource: PokemonServerDataSource
    ): PokemonRemoteDataSource

}
