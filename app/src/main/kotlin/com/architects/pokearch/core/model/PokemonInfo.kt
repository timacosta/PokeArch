package com.architects.pokearch.core.model

import com.google.gson.annotations.SerializedName
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
    @SerializedName("base_experience") val experience: Int,
    @SerializedName("types") val types: List<TypeResponse>,


    val hp: Int = Random.nextInt(maxHp),
    val attack: Int = Random.nextInt(maxAttack),
    val defense: Int = Random.nextInt(maxDefense),
    val speed: Int = Random.nextInt(maxSpeed),
    val exp: Int = Random.nextInt(maxExp)
) {
    companion object {
        const val maxHp = 300
        const val maxAttack = 300
        const val maxDefense = 300
        const val maxSpeed = 300
        const val maxExp = 1000
    }
}

data class TypeResponse(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: Type
)

data class Type(
    @SerializedName("name")
    val name: String
)
