@file:Suppress("unused")

package com.architects.pokearch.core.di

import com.architects.pokearch.data.repository.MediaPlayerRepository
import com.architects.pokearch.data.repository.PokeArchRepository
import com.architects.pokearch.data.repository.SensorRepository
import com.architects.pokearch.data.repository.VibrationRepository
import com.architects.pokearch.domain.repository.MediaPlayerRepositoryContract
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.domain.repository.SensorRepositoryContract
import com.architects.pokearch.domain.repository.VibrationRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("UnnecessaryAbstractClass")
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

    @Binds
    abstract fun bindsVibrationRepository(
        vibrationRepository: VibrationRepository
    ): VibrationRepositoryContract
}
