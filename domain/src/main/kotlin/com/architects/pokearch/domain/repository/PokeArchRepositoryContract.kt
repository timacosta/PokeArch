package com.architects.pokearch.domain.repository

import arrow.core.Either
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure
import kotlinx.coroutines.flow.Flow

interface PokeArchRepositoryContract {

    suspend fun getPokemonList(
        filter: String = "",
        page: Int = 0,
        limit: Int = 20,
    ): List<Pokemon>

    fun getPokemonTeam(): Flow<List<PokemonInfo>>

    suspend fun fetchPokemonList(): Failure?

    fun fetchPokemonInfo(id: suspend () -> Int): Flow<Either<Failure, PokemonInfo>>

    suspend fun updatePokemonInfo(pokemonInfo: PokemonInfo)

    suspend fun fetchCry(name: String): String

    fun randomPokemon(): Flow<Either<Failure, PokemonInfo>>
}
