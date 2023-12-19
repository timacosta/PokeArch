package com.architects.pokearch.core.framework.datasource

import android.util.Log
import arrow.core.Either
import com.architects.pokearch.core.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.core.data.network.mappers.toDomain
import com.architects.pokearch.core.data.network.service.CryService
import com.architects.pokearch.core.data.network.service.PokedexService
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.model.error.Failure
import javax.inject.Inject

class PokemonServerDataSource @Inject constructor(
    private val pokedexService: PokedexService,
    private val cryService: CryService
) : PokemonRemoteDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {

        val results = pokedexService.fetchPokemonList(limit, offset).body()?.results

        return if (results.isNullOrEmpty()) emptyList() else results.toDomain()
    }

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
            response.isSuccessful ->
                // If the response is successful, we are sure that the body is not null
                Either.Right(response.body()!!.toDomain())

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
