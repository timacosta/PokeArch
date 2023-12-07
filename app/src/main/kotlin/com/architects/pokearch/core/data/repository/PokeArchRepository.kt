package com.architects.pokearch.core.data.repository

import arrow.core.Either
import com.architects.pokearch.core.data.database.dao.PokemonDao
import com.architects.pokearch.core.data.database.dao.PokemonInfoDao
import com.architects.pokearch.core.data.mappers.PokemonEntityMapper
import com.architects.pokearch.core.data.mappers.PokemonInfoEntityMapper
import com.architects.pokearch.core.data.network.service.PokedexService
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.domain.model.Failure
import com.architects.pokearch.core.data.model.NetworkPokemon
import com.architects.pokearch.core.data.model.NetworkPokemonInfo
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeArchRepository(
    private val pokedexService: PokedexService,
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao,
    ) : PokeArchRepositoryContract {

    companion object {
        private const val LIMIT_ALL = 10000
    }

    override suspend fun fetchPokemonList(
        filter: String,
        page: Int,
        limit: Int,
    ): Flow<Either<Failure, List<Pokemon>>> = flow {
        val offset = page * limit
        emit(Either.Right(PokemonEntityMapper.asDomain(pokemonDao.getPokemonList(filter, limit, offset))))

        if (thereArePokemonsRemote()) {
            emit(getRemotePokemonList(filter, limit, offset))
        }
    }

    private suspend fun thereArePokemonsRemote() =
        pokedexService.fetchPokemonList(1, pokemonDao.countPokemonList()).let { responseCount ->
            when {
                responseCount.isSuccessful -> {
                    responseCount.body()?.next != null
                }

                else -> false
            }
        }

    private suspend fun getRemotePokemonList(
        filter: String,
        limit: Int,
        offset: Int,
    ) =
        pokedexService.fetchPokemonList(LIMIT_ALL, 0).let { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { pokemonResponse ->
                        pokemonDao.insertPokemonList(PokemonEntityMapper.asEntity(pokemonResponse.results))
                        Either.Right(PokemonEntityMapper.asDomain(pokemonDao.getPokemonList(filter, limit, offset)))
                    } ?: Either.Left(Failure.UnknownError)
                }

                else -> Either.Left(Failure.UnknownError)
            }
        }


    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {
        val pokemon = pokemonInfoDao.getPokemonInfo(id)?.let { pokemon ->
            emit(Either.Right(PokemonInfoEntityMapper.asDomain(pokemon)))
        }

        if (pokemon == null) {
            emit(getRemotePokemon(id))
        }
    }

    private suspend fun getRemotePokemon(
        id: Int,
    ): Either<Failure, PokemonInfo> {
        val response = pokedexService.fetchPokemonInfo(id)

        return when {
            response.isSuccessful -> {
                response.body()?.let {
                    pokemonInfoDao.insertPokemonInfo(PokemonInfoEntityMapper.asEntity(it))
                    pokemonInfoDao.getPokemonInfo(id)?.let { entity ->
                        Either.Right(PokemonInfoEntityMapper.asDomain(entity))
                    }
                } ?: Either.Left(Failure.UnknownError)
            }

            else -> Either.Left(Failure.UnknownError)
        }
    }
}
