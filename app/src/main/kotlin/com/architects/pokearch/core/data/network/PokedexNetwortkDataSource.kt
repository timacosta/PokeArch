package com.architects.pokearch.core.data.network

import arrow.core.Either
import com.architects.pokearch.core.data.mappers.PokemonEntityMapper
import com.architects.pokearch.core.data.mappers.PokemonInfoEntityMapper
import com.architects.pokearch.core.data.network.service.PokedexService
import com.architects.pokearch.core.data.repository.PokeArchRepository
import com.architects.pokearch.core.domain.model.Failure
import com.architects.pokearch.core.domain.model.PokemonInfo
import javax.inject.Inject

class PokedexNetwortkDataSource @Inject constructor(
    val pokedexService: PokedexService
){

    companion object {
        private const val LIMIT_ALL = 10000
    }
    suspend fun areMorePokemonAvailableRemote(countPokemonList: Int) =
        pokedexService.fetchPokemonList(1, countPokemonList).let { responseCount ->
            when {
                responseCount.isSuccessful -> {
                    responseCount.body()?.next != null
                }
                else -> false
            }
        }

    suspend fun getRemotePokemonList(
        filter: String,
        limit: Int,
        offset: Int
    ) =
        pokedexService.fetchPokemonList(LIMIT_ALL, 0)
            //TODO: MOVE TODO REPOSITORY
            .let { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { pokemonResponse ->
                        pokemonDao.insertPokemonList(PokemonEntityMapper.asEntity(pokemonResponse.results))
                        Either.Right(
                            PokemonEntityMapper.asDomain(
                                pokemonDao.getPokemonList(
                                    filter,
                                    limit,
                                    offset
                                )
                            )
                        )
                    } ?: Either.Left(Failure.UnknownError)
                }

                else -> Either.Left(Failure.UnknownError)
            }
        }

    suspend fun getRemotePokemon(
        id: Int,
    ): Either<Failure, PokemonInfo> {

        //TODO: MOVE TODO REPOSITORY?
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
