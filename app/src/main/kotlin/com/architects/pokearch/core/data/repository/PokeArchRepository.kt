package com.architects.pokearch.core.data.repository

import arrow.core.Either
import com.architects.pokearch.core.data.local.LocalDataSource
import com.architects.pokearch.core.data.network.RemoteDataSource
import com.architects.pokearch.core.data.network.mappers.toDomain
import com.architects.pokearch.core.domain.model.Failure
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokeArchRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PokeArchRepositoryContract {

    companion object {
        private const val PREFIX_URL = "https://play.pokemonshowdown.com/audio/cries/"
        private const val SUBFIX_URL = ".mp3"
    }

    override suspend fun fetchPokemonList(
        filter: String,
        page: Int,
        limit: Int,
    ): Flow<Either<Failure, List<Pokemon>>> = flow {

        val offset = page * limit
        val isMorePokemonAvailable =
            remoteDataSource.areMorePokemonAvailableFromRemote(localDataSource.numPokemonInDatabase())

        val pokemonListDb = localDataSource.getPokemonListFromDatabase(
            filter,
            limit,
            offset
        )

        val remotePokemonList = remoteDataSource.getPokemonListFromRemote(limit, offset)

        emit(Either.Right(pokemonListDb))

        if (isMorePokemonAvailable) {
            emit(remotePokemonList
                .let { responseFromService ->
                    when {
                        responseFromService.isSuccessful -> {
                            responseFromService.body()?.let { pokemonResponse ->
                                localDataSource.save(
                                    pokemonResponse.results.toDomain()
                                )
                                Either.Right(pokemonListDb)
                            } ?: Either.Left(Failure.UnknownError)
                        }

                        else -> Either.Left(Failure.UnknownError)
                    }
                }
            )
        }
    }

    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {
        val pokemon = localDataSource.getPokemonInfo(id)?.let { pokemon ->
            emit(Either.Right(pokemon))
        }

        if (pokemon == null) {
            emit(remoteDataSource.getOnePokemonFromRemote(id))
        }
    }

    override suspend fun fetchCry(name: String): String {
        var result = ""
        remoteDataSource.tryCatchCry(name) { result = it }
        if (name.contains("-") && result.isEmpty()) {
            remoteDataSource.tryCatchCry(name.replace("-", "")) { result = it }
            remoteDataSource.tryCatchCry(name.split("-")[0]) { result = it }
        }
        return "$PREFIX_URL$result$SUBFIX_URL"
    }

    override suspend fun randomPokemon(): Flow<Either<Failure, PokemonInfo>> {
        return fetchPokemonInfo(localDataSource.randomId())
    }
}

