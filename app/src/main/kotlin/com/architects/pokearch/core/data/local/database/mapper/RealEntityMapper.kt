package com.architects.pokearch.core.data.local.database.mapper

import com.architects.pokearch.core.data.local.database.entities.PokemonEntity
import com.architects.pokearch.core.data.local.database.entities.PokemonInfoEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatsEntity
import com.architects.pokearch.core.data.local.database.entities.converters.StatsHolder
import com.architects.pokearch.core.data.local.database.entities.converters.TypesHolder
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.model.Stat
import com.architects.pokearch.core.domain.model.Stat.Companion.ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.DEF
import com.architects.pokearch.core.domain.model.Stat.Companion.HP
import com.architects.pokearch.core.domain.model.Stat.Companion.SPEED
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_ATK
import com.architects.pokearch.core.domain.model.Stat.Companion.SP_DEF
import com.architects.pokearch.core.domain.model.Stats
import com.architects.pokearch.core.domain.model.Type
import com.architects.pokearch.core.domain.model.Types

//TODO: CHANGE NAME INTO ENTITYMAPPER AFTER SHOW CODE
fun List<PokemonEntity>.toDomain(): List<Pokemon> =
    map { it.toDomain() }

fun PokemonEntity.toDomain(): Pokemon =
    Pokemon(name = name, url = url)

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

fun List<TypesHolder>.toDomainTypes(): List<Types> =
    flatMap { it.toDomain() }

fun TypesHolder.toDomain(): List<Types> =
    types.map {
        Types(
            slot = it.slot,
            type = Type(it.type.name)
        )
    }
