package com.architects.pokearch.data.datasource

import arrow.core.Either
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.error.Failure
import com.architects.pokearch.domain.model.PokemonInfo

interface PokemonRemoteDataSource {
    companion object {
        private const val LIMIT_ALL = 10000
    }

    suspend fun getPokemonList(limit: Int = LIMIT_ALL, offset: Int = 0): Either<Failure, List<Pokemon>>

    suspend fun areMorePokemonAvailableFrom(count: Int): Boolean

    suspend fun getPokemon(id: Int): Either<Failure, PokemonInfo>

    suspend fun tryCatchCry(name: String, isSuccessful: (String) -> Unit)
}