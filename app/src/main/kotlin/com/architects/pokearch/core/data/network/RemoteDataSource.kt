package com.architects.pokearch.core.data.network

import android.util.Log
import arrow.core.Either
import com.architects.pokearch.core.data.network.mappers.toDomain
import com.architects.pokearch.core.data.network.service.CryService
import com.architects.pokearch.core.data.network.service.PokedexService
import com.architects.pokearch.core.domain.model.Failure
import com.architects.pokearch.core.domain.model.PokemonInfo
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val pokedexService: PokedexService,
    private val cryService: CryService
) {

    companion object {
        private const val LIMIT_ALL = 10000
    }

    suspend fun getPokemonListFromRemote(limit: Int = LIMIT_ALL, offset: Int = 0) =
        pokedexService.fetchPokemonList(limit, offset)

    suspend fun areMorePokemonAvailableFromRemote(numPokemonInDatabase: Int) =
        getPokemonListFromRemote(
            limit = 1,
            offset = numPokemonInDatabase
        ).let { responseFromRemote ->
            when {
                responseFromRemote.isSuccessful -> responseFromRemote.body()?.next != null

                else -> false
            }
        }

    suspend fun getOnePokemonFromRemote(id: Int): Either<Failure, PokemonInfo> {

        val response = pokedexService.fetchPokemonInfo(id)

        return when {
            response.isSuccessful ->
                // If the response is successful, we are sure that the body is not null
                Either.Right(response.body()!!.toDomain())

            else -> Either.Left(Failure.UnknownError)
        }
    }

     suspend fun tryCatchCry(name: String, isSuccessful: (String) -> Unit) {
        try {
            val fetchCry = cryService.thereIsCry(name)
            if (fetchCry.isSuccessful) isSuccessful(name)
        } catch (e: Exception) {
            Log.e("CryException", e.stackTraceToString())
        }
    }
}
