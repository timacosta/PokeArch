package com.architects.pokearch.data.datasource

import arrow.core.Either
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure

interface PokemonRemoteDataSource {
    companion object {
        private const val LIMIT_ALL = 10000
    }

    suspend fun getPokemonList(limit: Int = LIMIT_ALL, offset: Int = 0): Either<Failure, List<Pokemon>>

    suspend fun areMorePokemonAvailableFrom(count: Int): Either<Failure,Boolean>

    suspend fun getPokemon(id: Int): Either<Failure, PokemonInfo>

    suspend fun tryCatchCry(pokemonName: String, isSuccessful: (Either<Failure, String>) -> Unit)
}
