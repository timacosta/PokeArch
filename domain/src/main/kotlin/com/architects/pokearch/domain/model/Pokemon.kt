package com.architects.pokearch.domain.model

import com.architects.pokearch.domain.util.PokemonImageUrlBuilder

data class Pokemon(
    val name: String,
    val url: String
) {
    fun getIndex(): Int = url.split("/".toRegex()).dropLast(1).last().toIntOrNull() ?: 0
    fun getOfficialArtworkImageUrl(): String {
        return PokemonImageUrlBuilder.getOfficialArtworkImageUrlFrom(url)
    }
}
