package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.local.database.entities.converters.StatEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatsEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatsHolder
import com.architects.pokearch.core.data.network.model.NetworkStat
import com.architects.pokearch.core.data.network.model.NetworkStats
import com.architects.pokearch.core.domain.model.Stat
import com.architects.pokearch.core.domain.model.Stat.Companion.ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.DEF
import com.architects.pokearch.core.domain.model.Stat.Companion.HP
import com.architects.pokearch.core.domain.model.Stat.Companion.SPEED
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_DEF
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
