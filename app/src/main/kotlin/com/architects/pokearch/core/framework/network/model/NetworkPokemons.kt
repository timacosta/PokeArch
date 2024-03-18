package com.architects.pokearch.core.framework.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemons(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<NetworkPokemon>,
)

data class NetworkPokemon(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
