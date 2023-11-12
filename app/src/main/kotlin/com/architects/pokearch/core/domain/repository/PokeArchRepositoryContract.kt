package com.architects.pokearch.core.domain.repository

import arrow.core.Either
import com.architects.pokearch.core.model.Failure
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.core.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokeArchRepositoryContract {
    suspend fun fetchPokemonList(
        filter: String = "",
        page: Int = 0,
        limit: Int = 20
    ): Flow<Either<Failure, List<Pokemon>>>
    suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>>
}
