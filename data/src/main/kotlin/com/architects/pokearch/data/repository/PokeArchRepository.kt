package com.architects.pokearch.data.repository

import arrow.core.Either
import com.architects.pokearch.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokeArchRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) : PokeArchRepositoryContract {

    companion object {
        private const val PREFIX_URL = "https://play.pokemonshowdown.com/audio/cries/"
        private const val SUBFIX_URL = ".mp3"
    }

    override fun getPokemonTeam(): Flow<List<PokemonInfo>> = localDataSource.getPokemonTeam()


    override suspend fun getPokemonList(filter: String, page: Int, limit: Int): List<Pokemon> {
        val offset = page * limit

        return localDataSource.getPokemonList(filter, limit, offset)

    }

    override suspend fun fetchPokemonList(): Failure? {
        if (remoteDataSource.areMorePokemonAvailableFrom(localDataSource.countPokemon())) {
            val remotePokemonList = remoteDataSource.getPokemonList()

            return remotePokemonList.fold(
                ifRight = { pokemonList ->
                    localDataSource.savePokemonList(pokemonList)
                    null
                },
                ifLeft = { Failure.UnknownError }
            )
        }
        return null
    }

    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {
        val pokemon = localDataSource.getPokemonInfo(id)?.let { pokemon ->
            emit(Either.Right(pokemon))
        }

        if (pokemon == null) {
            emit(getRemotePokemon(id))
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
            }
        )

    //TODO: RESPONSIBILITY HERE OR SERVER DATASOURCE
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
