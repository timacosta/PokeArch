package com.architects.pokearch.core.model

import com.architects.pokearch.core.data.database.entities.PokemonEntity
import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun getIndex():Int = url.split("/".toRegex()).dropLast(1).last().toIntOrNull() ?: 0
    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/${getIndex()}.png"
    }
}

fun PokemonEntity.asPokemon() = Pokemon(name, url)

