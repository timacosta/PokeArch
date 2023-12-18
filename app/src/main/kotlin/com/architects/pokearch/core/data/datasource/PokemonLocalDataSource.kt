package com.architects.pokearch.core.data.datasource

import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo

interface PokemonLocalDataSource {
    suspend fun getPokemonListFromDatabase(
        filter: String,
        limit: Int,
        offset: Int
    ): List<Pokemon>

    suspend fun savePokemonList(pokemonList: List<Pokemon>)

    suspend fun savePokemonInfo(pokemonInfo: PokemonInfo)

    suspend fun getPokemonInfo(id: Int): PokemonInfo?

    suspend fun numPokemonInDatabase(): Int

    suspend fun randomId(): Int
}
