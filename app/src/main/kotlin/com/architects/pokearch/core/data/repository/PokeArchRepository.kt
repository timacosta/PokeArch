package com.architects.pokearch.core.data.repository

import arrow.core.Either
import com.architects.pokearch.core.data.database.dao.PokemonDao
import com.architects.pokearch.core.data.database.dao.PokemonInfoDao
import com.architects.pokearch.core.data.network.service.PokedexService
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.model.Failure
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.core.model.PokemonInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeArchRepository(
    private val pokedexService: PokedexService,
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao

) : PokeArchRepositoryContract {

    override suspend fun fetchPokemonList(
        filter: String,
        page: Int,
        limit: Int
    ): Flow<Either<Failure, List<Pokemon>>> = flow {

        val response = pokedexService.fetchPokemonList()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(Either.Right(it.results))
            }
        }
    }
    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {

        val response = pokedexService.fetchPokemonInfo(id)

        if (response.isSuccessful) {
            response.body()?.let {
                emit(Either.Right(it))
            }
        }
    }
}
