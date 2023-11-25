package com.architects.pokearch.core.domain.di

import com.architects.pokearch.core.data.database.dao.PokemonDao
import com.architects.pokearch.core.data.database.dao.PokemonInfoDao
import com.architects.pokearch.core.data.network.service.CryService
import com.architects.pokearch.core.data.network.service.PokedexService
import com.architects.pokearch.core.data.repository.PokeArchRepository
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun providePokeArchRepository(
        pokedexService: PokedexService,
        cryService: CryService,
        pokemonDao: PokemonDao,
        pokemonInfoDao: PokemonInfoDao
    ): PokeArchRepositoryContract {
        return PokeArchRepository(
            pokedexService,
            cryService,
            pokemonDao,
            pokemonInfoDao
        )
    }
}
