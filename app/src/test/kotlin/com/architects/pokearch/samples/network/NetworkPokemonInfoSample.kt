package com.architects.pokearch.samples.network

import com.architects.pokearch.core.framework.network.model.NetworkPokemonInfo
import com.architects.pokearch.core.framework.network.model.NetworkStat
import com.architects.pokearch.core.framework.network.model.NetworkStats
import com.architects.pokearch.core.framework.network.model.NetworkType
import com.architects.pokearch.core.framework.network.model.NetworkTypes

fun networkPokemonInfoListBuilder(size: Int = 10): List<NetworkPokemonInfo> {
    var list = listOf<NetworkPokemonInfo>()
    for (i in 1..size) {
        list = list.plus(networkPokemonInfoBuilder(i))
    }
    return list
}

fun networkPokemonInfoBuilder(
    id: Int = 1,
    name: String = "pokemon$id",
    height: Int = id,
    weight: Int = id*10,
    experience: Int = id*10,
    types: List<NetworkTypes> = networkTypesBuilder("type$id", "type$id-2"),
    stats: List<NetworkStats> = networkStatsBuilder(id),
    team: Boolean = false
) = NetworkPokemonInfo(
        id = id,
        name = name,
        height = height,
        weight = weight,
        experience = experience,
        types = types,
        stats = stats,
        team = team
    )

fun networkTypesBuilder(vararg types: String = arrayOf("type1", "type1-2")): List<NetworkTypes> {
    var count: Int = -1
    return types.toList().map {
        count++
        NetworkTypes(
            slot = count,
            networkType = NetworkType(name = it))
    }
}

fun networkStatsBuilder(id: Int = 1): List<NetworkStats> {

    var value = id*10

    while (value > 300) {
        value -= 300
    }

    return listOf(
        NetworkStats(value = value, stat = NetworkStat(name = NetworkStat.HP)),
        NetworkStats(value = value, stat = NetworkStat(name = NetworkStat.ATK)),
        NetworkStats(value = value, stat = NetworkStat(name = NetworkStat.DEF)),
        NetworkStats(value = value, stat = NetworkStat(name = NetworkStat.SPEED)),
        NetworkStats(value = value, stat = NetworkStat(name = NetworkStat.SP_ATK)),
        NetworkStats(value = value, stat = NetworkStat(name = NetworkStat.SP_DEF))
    )
}
