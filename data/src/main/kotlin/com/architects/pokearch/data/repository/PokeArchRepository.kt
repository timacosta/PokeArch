package com.architects.pokearch.data.repository

import arrow.core.Either
import com.architects.pokearch.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeArchRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource,
) : PokeArchRepositoryContract {

    override fun getPokemonTeam(): Flow<List<PokemonInfo>> = localDataSource.getPokemonTeam()

    override suspend fun getPokemonList(filter: String, page: Int, limit: Int): List<Pokemon> {
        val offset = page * limit

        return localDataSource.getPokemonList(filter, limit, offset)
    }

    override suspend fun fetchPokemonList(): Failure? {
        val areMorePokemonAvailableFrom =
            remoteDataSource.areMorePokemonAvailableFrom(localDataSource.countPokemon())

        return areMorePokemonAvailableFrom.fold(
            ifLeft = { it },
            ifRight = { moreAvailable ->
                if (moreAvailable) {
                    val remotePokemonList = remoteDataSource.getPokemonList()
                    remotePokemonList.fold(
                        ifRight = { pokemonList ->
                            localDataSource.savePokemonList(pokemonList)
                            null
                        },
                        ifLeft = { it },
                    )
                } else {
                    null
                }
            },
        )
    }

    override fun fetchPokemonInfo(
        id: suspend () -> Int,
    ): Flow<Either<Failure, PokemonInfo>> = flow {
        val pokemon = localDataSource.getPokemonInfo(id())?.let { pokemon ->
            emit(Either.Right(pokemon))
        }

        if (pokemon == null) {
            emit(getRemotePokemon(id()))
        }
    }

    override suspend fun updatePokemonInfo(pokemonInfo: PokemonInfo) =
        localDataSource.savePokemonInfo(pokemonInfo)

    private suspend fun getRemotePokemon(
        id: Int,
    ): Either<Failure, PokemonInfo> = remoteDataSource.getPokemon(id)
        .fold(
            ifRight = { response ->
                localDataSource.savePokemonInfo(response)
                Either.Right(response)
            },
            ifLeft = { failure ->
                Either.Left(failure)
            },
        )

    override suspend fun fetchCry(name: String): String {
        var result = ""
        remoteDataSource.tryCatchCry(name) {
            it.fold(
                ifRight = { cry -> result = cry },
                ifLeft = { failure -> },
            )
        }
        return result
    }

    override fun randomPokemon(): Flow<Either<Failure, PokemonInfo>> {
        return fetchPokemonInfo { localDataSource.randomId() }
    }
}
