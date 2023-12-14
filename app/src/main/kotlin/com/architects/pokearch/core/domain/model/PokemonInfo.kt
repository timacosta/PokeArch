package com.architects.pokearch.core.domain.model

import com.architects.pokearch.core.data.model.NetworkPokemonInfo
import com.architects.pokearch.core.domain.model.Stat.Companion.ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.DEF
import com.architects.pokearch.core.domain.model.Stat.Companion.HP
import com.architects.pokearch.core.domain.model.Stat.Companion.SPEED
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_DEF

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
                HP -> NetworkPokemonInfo.maxHp
                ATK -> NetworkPokemonInfo.maxAttack
                DEF -> NetworkPokemonInfo.maxDefense
                SP_ATK -> NetworkPokemonInfo.maxSpAttack
                SP_DEF -> NetworkPokemonInfo.maxSpDefense
                SPEED -> NetworkPokemonInfo.maxSpeed
                else -> value
            }
        }
}

data class Stat(
    val name: String,
) {
    companion object {
        const val HP = "HP"
        const val ATK = "Atk"
        const val DEF = "Def"
        const val SP_ATK = "Sp. Atk"
        const val SP_DEF = "Sp. Def"
        const val SPEED = "Speed"
    }
}