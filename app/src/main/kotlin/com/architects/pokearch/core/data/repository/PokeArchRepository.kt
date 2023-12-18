package com.architects.pokearch.core.data.repository

import arrow.core.Either
import com.architects.pokearch.core.data.local.LocalDataSource
import com.architects.pokearch.core.data.network.RemoteDataSource
import com.architects.pokearch.core.data.network.mappers.toDomain
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.model.error.Failure
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

        val pokemonListDb = localDataSource.getPokemonListFromDatabase(
            filter,
            limit,
            offset
        )

        emit(Either.Right(pokemonListDb))

        val isMorePokemonAvailable =
            remoteDataSource.areMorePokemonAvailableFrom(localDataSource.numPokemonInDatabase())

        if (isMorePokemonAvailable) {

            val remotePokemonList = remoteDataSource.getPokemonList(limit, offset)

            val response = remotePokemonList.let { responseFromService ->
                when {
                    responseFromService.isSuccessful -> {

                        responseFromService.body()?.let { remotePokemonList ->

                            localDataSource.savePokemonList(
                                remotePokemonList.results.toDomain()
                            )
                            val updatedPokemonList = localDataSource.getPokemonListFromDatabase(
                                filter, limit, offset
                            )
                            Either.Right(updatedPokemonList)

                        } ?: Either.Left(Failure.UnknownError)
                    }

                    else -> Either.Left(Failure.UnknownError)
                }
            }
            emit(response)
        }
    }

    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {
        val pokemon = localDataSource.getPokemonInfo(id)?.let { pokemon ->
            emit(Either.Right(pokemon))
        }

        if (pokemon == null) {

            when (val remotePokemonInfo = remoteDataSource.getPokemon(id)) {
                is Either.Right ->
                    localDataSource.savePokemonInfo(remotePokemonInfo.value)

                else -> Failure.UnknownError
            }

            val updatedPokemonInfo = localDataSource.getPokemonInfo(id)

            updatedPokemonInfo?.let { updatedPokemon ->
                emit(Either.Right(updatedPokemon))
            }
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
