package com.architects.pokearch.core.domain.mappers

import com.architects.pokearch.core.data.local.database.entities.PokemonEntity
import com.architects.pokearch.core.data.local.database.entities.PokemonInfoEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatsEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatsHolder
import com.architects.pokearch.core.data.local.database.entities.converters.TypeEntity
import com.architects.pokearch.core.data.local.database.entities.converters.TypesEntity
import com.architects.pokearch.core.data.local.database.entities.converters.TypesHolder
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.model.Stats
import com.architects.pokearch.core.domain.model.Types

fun List<Pokemon>.toEntity(): List<PokemonEntity> = map {
    PokemonEntity(
        id = it.getIndex(),
        name = it.name,
        url = it.url
    )
}

fun PokemonInfo.toEntity(): PokemonInfoEntity =

    PokemonInfoEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        experience = experience,
        types = types.toEntity(),
        stats = stats.toEntity(),
        team = team
    )

fun List<Stats>.toEntity(): StatsHolder =
    StatsHolder(
        stats = map { model ->
            StatsEntity(
                value = model.value,
                stat = StatEntity(
                    name = model.stat.name
                )
            )
        }
    )

fun List<Types>.toEntity(): TypesHolder =
    TypesHolder(
        types = map { entity ->
            TypesEntity(
                slot = entity.slot,
                type = TypeEntity(
                    name = entity.type.name
                )
            )
        }
    )
