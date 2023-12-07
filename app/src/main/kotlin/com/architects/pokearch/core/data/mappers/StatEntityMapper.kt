package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.database.entities.converters.StatEntity
import com.architects.pokearch.core.data.database.entities.converters.StatsEntity
import com.architects.pokearch.core.data.database.entities.converters.StatsHolder
import com.architects.pokearch.core.data.model.NetworkStat
import com.architects.pokearch.core.data.model.NetworkStats
import com.architects.pokearch.core.domain.model.Stat
import com.architects.pokearch.core.domain.model.Stats

object StatEntityMapper : EntityMapper<List<NetworkStats>, List<Stats>, StatsHolder> {
    override fun asEntity(network: List<NetworkStats>): StatsHolder =
        StatsHolder(network.map(::mapToStatsEntity))

    private fun mapToStatsEntity(networkStats: NetworkStats) =
        StatsEntity(
            value = networkStats.value,
            stat = mapToStatEntity(networkStats.stat)
        )

    private fun mapToStatEntity(networkStat: NetworkStat) =
        StatEntity(name = networkStat.name)

    override fun asDomain(entity: StatsHolder): List<Stats> =
        entity.stats.map(::mapToStats)

    private fun mapToStats(statsEntity: StatsEntity) =
        Stats(
            value = statsEntity.value,
            stat = mapToStat(statsEntity.stat)
        )

    private fun mapToStat(statEntity: StatEntity) =
        Stat(
            name = when (statEntity.name) {
                "hp" -> "HP"
                "attack" -> "Atk"
                "defense" -> "Def"
                "speed" -> "Speed"
                "special-attack" -> "Sp. Atk"
                "special-defense" -> "Sp. Def"
                else -> statEntity.name
            }
        )
}
