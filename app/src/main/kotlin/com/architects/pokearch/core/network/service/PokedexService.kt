package com.architects.pokearch.core.network.service

import arrow.core.Either
import com.architects.pokearch.core.data.model.PokemonResponse
import com.architects.pokearch.core.model.Failure
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokedexService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): Response<PokemonResponse>
}
