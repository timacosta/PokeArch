package com.architects.pokearch.core.model

import com.architects.pokearch.core.model.PokemonInfo.Companion.maxAttack
import com.architects.pokearch.core.model.PokemonInfo.Companion.maxDefense
import com.architects.pokearch.core.model.PokemonInfo.Companion.maxHp
import com.architects.pokearch.core.model.PokemonInfo.Companion.maxSpAttack
import com.architects.pokearch.core.model.PokemonInfo.Companion.maxSpDefense
import com.architects.pokearch.core.model.PokemonInfo.Companion.maxSpeed
import com.google.gson.annotations.SerializedName
import java.util.Locale
import kotlin.random.Random

data class PokemonInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("base_experience")
    val experience: Int,
    @SerializedName("types")
    val types: List<TypeResponse>,
    @SerializedName("stats")
    val stats: List<StatsResponse>,
    val team: Boolean = false,
) {
    companion object {
        const val maxHp = 300
        const val maxAttack = 300
        const val maxSpAttack = 300
        const val maxDefense = 300
        const val maxSpDefense = 300
        const val maxSpeed = 300
    }

    fun capitalizedName(): String = name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$id.png"
    }
}

data class TypeResponse(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: Type,
)

data class Type(
    @SerializedName("name")
    val name: String,
)

data class StatsResponse(
    @SerializedName("base_stat")
    val value: Int,
    val stat: Stat,
) {
    val maxValue: Int
        get() {
            return when (stat.name) {
                "hp" -> maxHp
                "attack" -> maxAttack
                "defense" -> maxDefense
                "special-attack" -> maxSpAttack
                "special-defense" -> maxSpDefense
                "speed" -> maxSpeed
                else -> value
            }
        }
}

data class Stat(
    val name: String,
)
