package com.architects.pokearch.core.framework.network.model

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
)

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
