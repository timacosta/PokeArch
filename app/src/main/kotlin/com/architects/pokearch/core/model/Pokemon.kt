package com.architects.pokearch.core.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    var page: Int = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$index.png"
    }
}