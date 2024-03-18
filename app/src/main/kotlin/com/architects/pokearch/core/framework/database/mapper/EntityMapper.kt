package com.architects.pokearch.core.framework.database.mapper

import com.architects.pokearch.core.framework.database.entities.PokemonEntity
import com.architects.pokearch.core.framework.database.entities.PokemonInfoEntity
import com.architects.pokearch.core.framework.database.entities.converters.StatEntity
import com.architects.pokearch.core.framework.database.entities.converters.StatsEntity
import com.architects.pokearch.core.framework.database.entities.converters.StatsHolder
import com.architects.pokearch.core.framework.database.entities.converters.TypesHolder
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.Stat
import com.architects.pokearch.domain.model.Stat.Companion.ATK
import com.architects.pokearch.domain.model.Stat.Companion.DEF
import com.architects.pokearch.domain.model.Stat.Companion.HP
import com.architects.pokearch.domain.model.Stat.Companion.SPEED
import com.architects.pokearch.domain.model.Stat.Companion.SP_ATK
import com.architects.pokearch.domain.model.Stat.Companion.SP_DEF
import com.architects.pokearch.domain.model.Stats
import com.architects.pokearch.domain.model.Type
import com.architects.pokearch.domain.model.Types

fun List<PokemonEntity>.toDomain(): List<Pokemon> =
    map { it.toDomain() }

fun PokemonEntity.toDomain(): Pokemon =
    Pokemon(name = name, url = url)

fun List<PokemonInfoEntity>.toTeamDomain(): List<PokemonInfo> =
    map { it.toDomain() }

fun PokemonInfoEntity.toDomain(): PokemonInfo =
    PokemonInfo(
        id = id,
        name = name,
        height = height,
        weight = weight,
        experience = experience,
        types = types.toDomain(),
        stats = stats.toDomain(),
        team = team
    )

fun StatsHolder.toDomain(): List<Stats> =
    stats.map { it.toDomain() }

fun StatsEntity.toDomain(): Stats =
    Stats(
        value = value,
        stat = stat.toDomain()
    )

fun StatEntity.toDomain(): Stat =
    Stat(
        name = when (name) {
            "hp" -> HP
            "attack" -> ATK
            "defense" -> DEF
            "speed" -> SPEED
            "special-attack" -> SP_ATK
            "special-defense" -> SP_DEF
            else -> name
        }
    )

fun TypesHolder.toDomain(): List<Types> =
    types.map {
        Types(
            slot = it.slot,
            type = Type(it.type.name)
        )
    }
