package com.architects.pokearch.core.framework.mappers

import com.architects.pokearch.core.framework.local.entities.converters.StatEntity
import com.architects.pokearch.core.framework.local.entities.converters.StatsEntity
import com.architects.pokearch.core.framework.local.entities.converters.StatsHolder
import com.architects.pokearch.core.framework.network.model.NetworkStat
import com.architects.pokearch.core.framework.network.model.NetworkStats
import com.architects.pokearch.core.domain.model.Stat
import com.architects.pokearch.core.domain.model.Stat.Companion.ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.DEF
import com.architects.pokearch.core.domain.model.Stat.Companion.HP
import com.architects.pokearch.core.domain.model.Stat.Companion.SPEED
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_DEF
import com.architects.pokearch.core.domain.model.Stats

object StatEntityMapper : EntityMapper<List<NetworkStats>, List<Stats>, StatsHolder> {
    override fun asEntityFrom(network: List<NetworkStats>): StatsHolder =
        StatsHolder(network.map(::mapToStatsEntityFrom))

    private fun mapToStatsEntityFrom(networkStats: NetworkStats) =
        StatsEntity(
            value = networkStats.value,
            stat = mapToStatEntityFrom(networkStats.stat)
        )

    private fun mapToStatEntityFrom(networkStat: NetworkStat) =
        StatEntity(name = networkStat.name)

    override fun asDomainFrom(entity: StatsHolder): List<Stats> =
        entity.stats.map(::mapToStatsFrom)

    private fun mapToStatsFrom(statsEntity: StatsEntity) =
        Stats(
            value = statsEntity.value,
            stat = mapToStatFrom(statsEntity.stat)
        )

    private fun mapToStatFrom(statEntity: StatEntity) =
        Stat(
            name = when (statEntity.name) {
                "hp" -> HP
                "attack" -> ATK
                "defense" -> DEF
                "speed" -> SPEED
                "special-attack" -> SP_ATK
                "special-defense" -> SP_DEF
                else -> statEntity.name
            }
        )
}

