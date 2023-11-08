package com.architects.pokearch.core.domain.di

import com.architects.pokearch.core.data.repository.PokeArchRepository
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.network.service.PokedexService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun providePokeArchRepository(pokedexService: PokedexService): PokeArchRepositoryContract {
        return PokeArchRepository(pokedexService)
    }
}
