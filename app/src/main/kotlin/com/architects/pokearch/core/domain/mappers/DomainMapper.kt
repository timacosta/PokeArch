package com.architects.pokearch.core.domain.mappers

import com.architects.pokearch.core.framework.local.entities.PokemonEntity
import com.architects.pokearch.core.framework.local.entities.PokemonInfoEntity
import com.architects.pokearch.core.framework.local.entities.converters.StatEntity
import com.architects.pokearch.core.framework.local.entities.converters.StatsEntity
import com.architects.pokearch.core.framework.local.entities.converters.StatsHolder
import com.architects.pokearch.core.framework.local.entities.converters.TypeEntity
import com.architects.pokearch.core.framework.local.entities.converters.TypesEntity
import com.architects.pokearch.core.framework.local.entities.converters.TypesHolder
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
