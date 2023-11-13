package com.architects.pokearch.core.domain.repository

import arrow.core.Either
import com.architects.pokearch.core.data.model.PokemonResponse
import com.architects.pokearch.core.model.Failure
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.core.model.PokemonInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeArchRepositoryContract {
    suspend fun fetchPokemonList(): Flow<Either<Failure, List<Pokemon>>>
    suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>>
}
