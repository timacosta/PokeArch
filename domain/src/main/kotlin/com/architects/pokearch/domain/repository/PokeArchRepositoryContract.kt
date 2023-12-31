package com.architects.pokearch.domain.repository

import arrow.core.Either
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokeArchRepositoryContract {

    suspend fun getPokemonList(
        filter: String = "",
        page: Int = 0,
        limit: Int = 20
    ): List<Pokemon>

    suspend fun fetchPokemonList(): Failure?

    suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>>

    suspend fun fetchCry(name: String): String

    suspend fun randomPokemon(): Flow<Either<Failure, PokemonInfo>>
}
