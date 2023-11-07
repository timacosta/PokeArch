package com.architects.pokearch.core.domain.model

data class Pokemon(
    val name: String,
    val url: String
){
    fun getId():Int =
        url.split("/".toRegex()).dropLast(1).last().toIntOrNull() ?: 0
    fun getImageUrl(): String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/${getId()}.png"

}
