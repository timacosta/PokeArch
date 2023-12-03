package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.database.entities.StatEntity
import com.architects.pokearch.core.data.database.entities.StatsEntity
import com.architects.pokearch.core.data.database.entities.StatsHolder
import com.architects.pokearch.core.model.Stat
import com.architects.pokearch.core.model.StatsResponse

object StatEntityMapper : EntityMapper<List<StatsResponse>, StatsHolder>{
    override fun asEntity(domain: List<StatsResponse>): StatsHolder =
        StatsHolder(domain.map(::mapToStatsEntity))

    private fun mapToStatsEntity(statsResponse: StatsResponse) =
        StatsEntity(
            value = statsResponse.value,
            stat = mapToStatEntity(statsResponse.stat)
        )

    private fun mapToStatEntity(stat: Stat) =
        StatEntity(name = stat.name)

    override fun asDomain(entity: StatsHolder): List<StatsResponse> =
        entity.stats.map(::mapToStatResponse)

    private fun mapToStatResponse(statsEntity: StatsEntity) =
        StatsResponse(
            value = statsEntity.value,
            stat = mapToStat(statsEntity.stat)
        )

    private fun mapToStat(statEntity: StatEntity) =
        Stat(name = statEntity.name)
}
