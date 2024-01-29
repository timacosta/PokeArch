package com.architects.pokearch.data.datasource

import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokemonLocalDataSource {
    suspend fun getPokemonList(
        filter: String,
        limit: Int,
        offset: Int
    ): List<Pokemon>

    fun getPokemonTeam(): Flow<List<PokemonInfo>>

    suspend fun savePokemonList(pokemonList: List<Pokemon>)

    suspend fun savePokemonInfo(pokemonInfo: PokemonInfo)

    suspend fun getPokemonInfo(id: Int): PokemonInfo?

    suspend fun countPokemon(): Int

    suspend fun randomId(): Int
}
