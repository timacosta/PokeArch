package com.architects.pokearch.core.data.network.service

import com.architects.pokearch.core.data.model.PokemonResponse
import com.architects.pokearch.core.model.PokemonInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): Response<PokemonResponse>

    @GET("pokemon/{id}")
    suspend fun fetchPokemonInfo(@Path("id") id: Int): Response<PokemonInfo>
}
