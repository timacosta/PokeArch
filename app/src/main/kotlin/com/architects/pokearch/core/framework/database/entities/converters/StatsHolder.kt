package com.architects.pokearch.core.framework.database.entities.converters

data class StatsHolder (val stats: List<StatsEntity>)

data class StatsEntity(
    val value: Int,
    val stat: StatEntity
)

data class StatEntity(
    val name: String
)
