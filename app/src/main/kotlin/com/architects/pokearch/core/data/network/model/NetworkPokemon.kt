package com.architects.pokearch.core.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemon(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun getIndex():Int = url.split("/".toRegex()).dropLast(1).last().toIntOrNull() ?: 0
}
