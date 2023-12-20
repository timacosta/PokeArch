package com.architects.pokearch.core.framework.network.mappers

import com.architects.pokearch.core.framework.network.model.NetworkPokemon
import com.architects.pokearch.core.framework.network.model.NetworkPokemonInfo
import com.architects.pokearch.core.framework.network.model.NetworkStat
import com.architects.pokearch.core.framework.network.model.NetworkStats
import com.architects.pokearch.core.framework.network.model.NetworkTypes
import com.architects.pokearch.core.domain.model.Pokemon
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


fun List<NetworkPokemon>.toDomain(): List<Pokemon> = map {
    Pokemon(
        name = it.name,
        url = it.url
    )
}
fun NetworkPokemonInfo.toDomain(): PokemonInfo =

    PokemonInfo(
        id = id,
        name = name,
        height = height,
        weight = weight,
        experience = experience,
        types = types.toDomainTypes(),
        stats = stats.toDomainStats(),
        team = team
    )

fun List<NetworkStats>.toDomainStats(): List<Stats> =
    map { it.toDomainStats() }

fun NetworkStats.toDomainStats(): Stats =
    Stats(
        value = value,
        stat = stat.toDomainStat()
    )

fun NetworkStat.toDomainStat(): Stat =
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


fun List<NetworkTypes>.toDomainTypes(): List<Types> =
    map { type ->
        Types(
            slot = type.slot,
            type = Type(
                name = type.networkType.name
            )
        )
    }
