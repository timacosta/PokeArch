package com.architects.pokearch.testing.samples

import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.Stat
import com.architects.pokearch.domain.model.Stats
import com.architects.pokearch.domain.model.Type
import com.architects.pokearch.domain.model.Types

private const val MULTIPLIER_BASE = 10
private const val STAT_MAX = 300

fun pokemonInfoListBuilder(size: Int = 10): List<PokemonInfo> {
    var list = listOf<PokemonInfo>()
    for (i in 1..size) {
        list = list.plus(pokemonInfoBuilder(i))
    }
    return list
}

fun pokemonInfoBuilder(
    id: Int = 1,
    name: String = "pokemon$id",
    height: Int = id,
    weight: Int = id * MULTIPLIER_BASE,
    experience: Int = id * MULTIPLIER_BASE,
    types: List<Types> = typesBuilder("type$id", "type$id-2"),
    stats: List<Stats> = statsBuilder(id),
    team: Boolean = false,
) = PokemonInfo(
    id = id,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
    types = types,
    stats = stats,
    team = team,
)

fun typesBuilder(vararg types: String = arrayOf("type1", "type1-2")): List<Types> {
    var count: Int = -1
    return types.toList().map {
        count++
        Types(
            slot = count,
            type = Type(name = it),
        )
    }
}

fun statsBuilder(id: Int = 1): List<Stats> {
    var value = id * MULTIPLIER_BASE

    while (value > STAT_MAX) {
        value -= STAT_MAX
    }

    return listOf(
        Stats(value = value, stat = Stat(name = Stat.HP)),
        Stats(value = value, stat = Stat(name = Stat.ATK)),
        Stats(value = value, stat = Stat(name = Stat.DEF)),
        Stats(value = value, stat = Stat(name = Stat.SPEED)),
        Stats(value = value, stat = Stat(name = Stat.SP_ATK)),
        Stats(value = value, stat = Stat(name = Stat.SP_DEF)),
    )
}
