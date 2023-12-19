package com.architects.pokearch.core.data.repository

import arrow.core.Either
import com.architects.pokearch.core.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.core.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.model.error.Failure
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokeArchRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) : PokeArchRepositoryContract {

    companion object {
        private const val LIMIT_ALL = 10000
        private const val PREFIX_URL = "https://play.pokemonshowdown.com/audio/cries/"
        private const val SUBFIX_URL = ".mp3"
    }

    override suspend fun getPokemonList(filter: String, page: Int, limit: Int): List<Pokemon> {
        val offset = page * limit

        val pokemonListDatabase = localDataSource.getPokemonListFromDatabase(filter, limit, offset)

        return pokemonListDatabase

    }

    override suspend fun fetchPokemonList(): Failure? =
        if (areMorePokemonAvailableRemote()) {
            getRemotePokemonList()
        } else null

    private suspend fun areMorePokemonAvailableRemote() =
        remoteDataSource.getPokemonList(1, localDataSource.numPokemonInDatabase()).let { responseCount ->
            when {
                responseCount.isSuccessful -> {
                    responseCount.body()?.next != null
                }

                else -> false
            }
        }

    private suspend fun getRemotePokemonList() =
        remoteDataSource.getPokemonList(LIMIT_ALL, 0).let { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { pokemonResponse ->
                        pokemonDao.insertPokemonList(PokemonEntityMapper.asEntity(pokemonResponse.results))
                        null
                    }
                }

                else -> Failure.UnknownError
            }
        }


    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {
        val pokemon = localDataSource.getPokemonInfo(id)?.let { pokemon ->
            emit(Either.Right(pokemon))
        }

        if (pokemon == null) {
            emit(getRemotePokemon(id))
        }
    }

    private suspend fun getRemotePokemon(
        id: Int,
    ): Either<Failure, PokemonInfo> {
        val response = remoteDataSource.getPokemon(id)

        return when {
            response.isSuccessful -> {
                response.body()?.let {
                    localDataSource.savePokemonInfo(it)
                    localDataSource.getPokemonInfo(id)?.let { entity ->
                        Either.Right(PokemonInfoEntityMapper.asDomain(entity))
                    }
                } ?: Either.Left(Failure.UnknownError)
            }

            else -> Either.Left(Failure.UnknownError)
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
