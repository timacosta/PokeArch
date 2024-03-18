package com.architects.pokearch.domain.util

object PokemonImageUrlBuilder {

    private const val BASE_IMAGE_URL =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/"
    private const val OFFICIAL_ARTWORK_URL = "pokemon/other/official-artwork/"
    private const val SMALL_SPRITE_ARTWORK_URL = "pokemon/"

    fun getOfficialArtworkImageUrl(pokemonId: Int): String {
        return BASE_IMAGE_URL + OFFICIAL_ARTWORK_URL + "$pokemonId.png"
    }

    fun getSpriteUrl(pokemonId: Int): String {
        return BASE_IMAGE_URL + SMALL_SPRITE_ARTWORK_URL + "$pokemonId.png"
    }

    fun getOfficialArtworkImageUrlFrom(url: String): String {
        val pokemonId = getIndex(url)
        return getOfficialArtworkImageUrl(pokemonId)
    }

    private fun getIndex(url: String): Int =
        url.split("/".toRegex()).dropLast(1).last().toIntOrNull() ?: 0
}
