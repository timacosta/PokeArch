package com.architects.pokearch.domain.model

private val BASE_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/"
private val OFFICIAL_ARTWORK_URL = "pokemon/other/official-artwork/"
private val SMALL_SPRITE_ARTWORK_URL = "pokemon/"

data class Pokemon(
    val name: String,
    val url: String,
) {
    fun getIndex():Int = url.split("/".toRegex()).dropLast(1).last().toIntOrNull() ?: 0
    fun getOfficialArtworkImageUrl(): String {
        return BASE_IMAGE_URL +
            OFFICIAL_ARTWORK_URL + "${getIndex()}.png"
    }

    //TODO: Remove after doing the item team, this will be in PokemonInfo
    fun getSmallSpriteArtworkImageUrl(): String {
        return BASE_IMAGE_URL +
                SMALL_SPRITE_ARTWORK_URL + "${getIndex()}.png"
    }
}
