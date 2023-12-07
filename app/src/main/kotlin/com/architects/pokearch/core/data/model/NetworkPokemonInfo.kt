package com.architects.pokearch.core.data.model

import com.architects.pokearch.core.data.model.NetworkPokemonInfo.Companion.maxAttack
import com.architects.pokearch.core.data.model.NetworkPokemonInfo.Companion.maxDefense
import com.architects.pokearch.core.data.model.NetworkPokemonInfo.Companion.maxHp
import com.architects.pokearch.core.data.model.NetworkPokemonInfo.Companion.maxSpAttack
import com.architects.pokearch.core.data.model.NetworkPokemonInfo.Companion.maxSpDefense
import com.architects.pokearch.core.data.model.NetworkPokemonInfo.Companion.maxSpeed
import com.google.gson.annotations.SerializedName

data class NetworkPokemonInfo(
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
    val types: List<NetworkTypes>,
    @SerializedName("stats")
    val stats: List<NetworkStats>,
    val team: Boolean = false,
) {
    companion object {
        const val maxHp = 150
        const val maxAttack = 150
        const val maxSpAttack = 150
        const val maxDefense = 150
        const val maxSpDefense = 150
        const val maxSpeed = 150
    }
}

data class NetworkTypes(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val networkType: NetworkType,
)

data class NetworkType(
    @SerializedName("name")
    val name: String,
)

data class NetworkStats(
    @SerializedName("base_stat")
    val value: Int,
    @SerializedName("stat")
    val stat: NetworkStat,
)

data class NetworkStat(
    val name: String,
)
