@file:Suppress("unused")

package com.architects.pokearch.core.domain.di

import com.architects.pokearch.core.data.repository.MediaPlayerRepository
import com.architects.pokearch.core.data.repository.PokeArchRepository
import com.architects.pokearch.core.data.repository.SensorRepository
import com.architects.pokearch.core.domain.repository.MediaPlayerRepositoryContract
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.domain.repository.SensorRepositoryContract
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
