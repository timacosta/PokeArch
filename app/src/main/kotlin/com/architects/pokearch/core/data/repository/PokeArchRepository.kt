package com.architects.pokearch.core.data.repository

import arrow.core.Either
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.model.Failure
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.core.network.service.PokedexService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeArchRepository(
    private val pokedexService: PokedexService,
) : PokeArchRepositoryContract {

    override suspend fun fetchPokemonList(): Flow<Either<Failure, List<Pokemon>>> = flow {

        //TODO: Add call to Room first when configured

        val response = pokedexService.fetchPokemonList()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(Either.Right(it.results))
            }
        }

        //TODO: Add missing Error Handling
    }
}