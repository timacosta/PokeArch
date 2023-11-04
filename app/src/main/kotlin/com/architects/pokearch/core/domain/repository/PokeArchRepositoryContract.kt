package com.architects.pokearch.core.domain.repository

import arrow.core.Either
import com.architects.pokearch.core.data.model.PokemonResponse
import com.architects.pokearch.core.model.Failure
import com.architects.pokearch.core.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokeArchRepositoryContract {
    suspend fun fetchPokemonList(): Flow<Either<Failure, List<Pokemon>>>
}
