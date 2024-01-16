package com.architects.pokearch.domain.model

private const val BASE_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/"
private const val OFFICIAL_ARTWORK_URL = "pokemon/other/official-artwork/"

data class Pokemon(
    val name: String,
    val url: String,
) {
    fun getIndex():Int = url.split("/".toRegex()).dropLast(1).last().toIntOrNull() ?: 0
    fun getOfficialArtworkImageUrl(): String {
        return BASE_IMAGE_URL +
            OFFICIAL_ARTWORK_URL + "${getIndex()}.png"
    }
}
