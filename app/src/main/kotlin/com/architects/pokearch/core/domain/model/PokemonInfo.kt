package com.architects.pokearch.core.domain.model

import com.architects.pokearch.core.data.model.NetworkPokemonInfo

data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val experience: Int,
    val types: List<Types>,
    val stats: List<Stats>,
    val team: Boolean = false,
) {
    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$id.png"
    }
}

data class Types(
    val slot: Int,
    val type: Type,
)

data class Type(
    val name: String,
)

data class Stats(
    val value: Int,
    val stat: Stat,
) {
    val maxValue: Int
        get() {
            return when (stat.name) {
                "HP" -> NetworkPokemonInfo.maxHp
                "Atk" -> NetworkPokemonInfo.maxAttack
                "Def" -> NetworkPokemonInfo.maxDefense
                "Sp. Atk" -> NetworkPokemonInfo.maxSpAttack
                "Sp. Def" -> NetworkPokemonInfo.maxSpDefense
                "Speed" -> NetworkPokemonInfo.maxSpeed
                else -> value
            }
        }
}

data class Stat(
    val name: String,
)
