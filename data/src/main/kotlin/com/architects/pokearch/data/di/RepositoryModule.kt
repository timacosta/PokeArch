@file:Suppress("unused")

package com.architects.pokearch.data.di

import com.architects.pokearch.data.repository.MediaPlayerRepository
import com.architects.pokearch.data.repository.PokeArchRepository
import com.architects.pokearch.data.repository.SensorRepository
import com.architects.pokearch.domain.repository.MediaPlayerRepositoryContract
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.domain.repository.SensorRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsPokeArchRepository(
        pokeArchRepository: PokeArchRepository
    ): PokeArchRepositoryContract

    @Binds
    abstract fun providesMediaPlayerRepository(
        mediaPlayerRepository: MediaPlayerRepository
    ): MediaPlayerRepositoryContract

    @Binds
    abstract fun provideSensorRepository(
        sensorRepository: SensorRepository
    ): SensorRepositoryContract
}
