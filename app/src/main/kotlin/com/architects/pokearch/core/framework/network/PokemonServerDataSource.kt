package com.architects.pokearch.core.framework.network

import android.util.Log
import arrow.core.Either
import com.architects.pokearch.core.framework.network.interceptor.InternetConnectivityException
import com.architects.pokearch.core.framework.network.mappers.toDomain
import com.architects.pokearch.core.framework.network.service.CryService
import com.architects.pokearch.core.framework.network.service.PokedexService
import com.architects.pokearch.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.data.mapper.ErrorMapper
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.ErrorType
import com.architects.pokearch.domain.model.error.Failure
import javax.inject.Inject

class PokemonServerDataSource @Inject constructor(
    private val pokedexService: PokedexService,
    private val cryService: CryService,
) : PokemonRemoteDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): Either<Failure, List<Pokemon>> {
        return try {
            val response = pokedexService.fetchPokemonList(limit, offset)
            if (response.isSuccessful) {
                Either.Right(response.body()?.results?.toDomain() ?: emptyList())
            } else {
                Either.Left(Failure.NetworkError(ErrorMapper.getErrorType(response.code())))
            }
        } catch (exception: Exception) {
            when (exception) {
                is InternetConnectivityException -> Either.Left(Failure.NetworkError(ErrorType.NoInternet))
                else -> Either.Left(Failure.UnknownError)
            }
        }
    }

    override suspend fun areMorePokemonAvailableFrom(count: Int): Either<Failure, Boolean> {
        return try {
            val remotePokemonList = pokedexService.fetchPokemonList(limit = 1, offset = count)

            return remotePokemonList.let { response ->
                when {
                    response.isSuccessful -> Either.Right(response.body()?.next != null)

                    else -> Either.Left(Failure.NetworkError(ErrorMapper.getErrorType(response.code())))
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is InternetConnectivityException -> Either.Left(Failure.NetworkError(ErrorType.NoInternet))
                else -> Either.Left(Failure.UnknownError)
            }
        }
    }

    override suspend fun getPokemon(id: Int): Either<Failure, PokemonInfo> {
        return try {
            val response = pokedexService.fetchPokemonInfo(id)
            return when {
                response.isSuccessful -> {
                    val pokemonInfo = response.body()?.toDomain()
                    pokemonInfo?.let {
                        Either.Right(it)
                    } ?: Either.Left(Failure.UnknownError)
                }

                else -> Either.Left(Failure.NetworkError(ErrorMapper.getErrorType(response.code())))
            }
        } catch (exception: Exception) {
            when (exception) {
                is InternetConnectivityException -> Either.Left(Failure.NetworkError(ErrorType.NoInternet))
                else -> Either.Left(Failure.UnknownError)
            }
        }

    }


    override suspend fun tryCatchCry(name: String, isSuccessful: (String) -> Unit) {
        try {
            val fetchCry = cryService.thereIsCry(name)
            if (fetchCry.isSuccessful) isSuccessful(name)
        } catch (exception: Exception) {
            Log.e("CryException", exception.stackTraceToString())
            when (exception) {
                is InternetConnectivityException -> Either.Left(Failure.NetworkError(ErrorType.NoInternet))
                else -> Either.Left(Failure.UnknownError)
            }
        }
    }
}
