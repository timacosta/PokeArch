package com.architects.pokearch.core.data.datasource

import arrow.core.Either
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.model.error.Failure

interface PokemonRemoteDataSource {
    companion object {
        private const val LIMIT_ALL = 10000
    }

    suspend fun getPokemonList(limit: Int = LIMIT_ALL, offset: Int = 0): List<Pokemon>

    suspend fun areMorePokemonAvailableFrom(count: Int): Boolean

    suspend fun getPokemon(id: Int): Either<Failure, PokemonInfo>

    suspend fun tryCatchCry(name: String, isSuccessful: (String) -> Unit)
}
