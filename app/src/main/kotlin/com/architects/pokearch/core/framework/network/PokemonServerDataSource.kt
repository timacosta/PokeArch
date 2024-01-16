package com.architects.pokearch.core.framework.network

import android.util.Log
import arrow.core.Either
import com.architects.pokearch.core.framework.network.mappers.toDomain
import com.architects.pokearch.core.framework.network.service.CryService
import com.architects.pokearch.core.framework.network.service.PokedexService
import com.architects.pokearch.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure
import javax.inject.Inject

class PokemonServerDataSource @Inject constructor(
    private val pokedexService: PokedexService,
    private val cryService: CryService
) : PokemonRemoteDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int):Either<Failure, List<Pokemon>> {

        val response = pokedexService.fetchPokemonList(limit, offset).let { response ->

            when {
                response.isSuccessful -> {
                    response.body()?.let { pokemonResponse ->

                        Either.Right(pokemonResponse.results.toDomain())

                    } ?: Either.Left(Failure.UnknownError)
                }

                else -> Either.Left(Failure.UnknownError)
            }
        }
        return response
    }

    //TODO: IMPLEMENT CONTROL NETWORK ERRORS
    override suspend fun areMorePokemonAvailableFrom(count: Int): Boolean {

        val remotePokemonList = pokedexService.fetchPokemonList(limit = 1, offset = count)

        return remotePokemonList.let { responseFromRemote ->
            when {
                responseFromRemote.isSuccessful -> responseFromRemote.body()?.next != null

                else -> false
            }
        }
    }

    override suspend fun getPokemon(id: Int): Either<Failure, PokemonInfo> {
        val response = pokedexService.fetchPokemonInfo(id)

        return when {
            response.isSuccessful -> {
                val pokemonInfo = response.body()?.toDomain()
                pokemonInfo?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.UnknownError)
            }
            else -> Either.Left(Failure.UnknownError)
        }
    }

    override suspend fun tryCatchCry(name: String, isSuccessful: (String) -> Unit) {
        try {
            val fetchCry = cryService.thereIsCry(name)
            if (fetchCry.isSuccessful) isSuccessful(name)
        } catch (e: Exception) {
            Log.e("CryException", e.stackTraceToString())
        }
    }
}
