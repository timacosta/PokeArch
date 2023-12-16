package com.architects.pokearch.core.domain.repository

import androidx.paging.PagingSource
import arrow.core.Either
import com.architects.pokearch.core.data.database.entities.PokemonEntity
import com.architects.pokearch.core.domain.model.Failure
import com.architects.pokearch.core.data.model.NetworkPokemon
import com.architects.pokearch.core.data.model.NetworkPokemonInfo
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokeArchRepositoryContract {
    suspend fun fetchPokemonList(
        filter: String = "",
        page: Int = 0,
        limit: Int = 20
    ): Flow<Either<Failure, List<Pokemon>>>

    suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>>

    suspend fun fetchCry(name: String): String

    suspend fun randomPokemon(): Flow<Either<Failure, PokemonInfo>>
}
