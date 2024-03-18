package com.architects.pokearch.samples.database

import com.architects.pokearch.core.framework.database.entities.PokemonInfoEntity
import com.architects.pokearch.core.framework.database.entities.converters.StatEntity
import com.architects.pokearch.core.framework.database.entities.converters.StatsEntity
import com.architects.pokearch.core.framework.database.entities.converters.StatsHolder
import com.architects.pokearch.core.framework.database.entities.converters.TypeEntity
import com.architects.pokearch.core.framework.database.entities.converters.TypesEntity
import com.architects.pokearch.core.framework.database.entities.converters.TypesHolder
import com.architects.pokearch.domain.model.Stat

fun pokemonInfoEntityListBuilder(size: Int = 10): List<PokemonInfoEntity> {
    var list = listOf<PokemonInfoEntity>()
    for (i in 1..size) {
        list = list.plus(pokemonInfoEntityBuilder(i))
    }
    return list
}

fun pokemonInfoEntityBuilder(
    id: Int = 1,
    name: String = "pokemon$id",
    height: Int = id,
    weight: Int = id*10,
    experience: Int = id*10,
    types: TypesHolder = typesHolderBuilder("type$id", "type$id-2"),
    stats: StatsHolder = statsHolderBuilder(id),
    team: Boolean = false
) = PokemonInfoEntity(
    id = id,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
    types = types,
    stats = stats,
    team = team
)

fun typesHolderBuilder(vararg types: String = arrayOf("type1", "type1-2")): TypesHolder =
    TypesHolder(typesEntityBuilder(*types))

fun typesEntityBuilder(vararg types: String = arrayOf("type1", "type1-2")): List<TypesEntity> {
    var count: Int = -1
    return types.toList().map {
        count++
        TypesEntity(
            slot = count,
            type = TypeEntity(name = it)
        )
    }
}

fun statsHolderBuilder(id: Int = 1): StatsHolder =
    StatsHolder(statsEntityBuilder(id))

fun statsEntityBuilder(id: Int = 1): List<StatsEntity> {
    var value = id*10

    while (value > 300) {
        value -= 300
    }

    return listOf(
        StatsEntity(value = value, stat = StatEntity(name = Stat.HP)),
        StatsEntity(value = value, stat = StatEntity(name = Stat.ATK)),
        StatsEntity(value = value, stat = StatEntity(name = Stat.DEF)),
        StatsEntity(value = value, stat = StatEntity(name = Stat.SPEED)),
        StatsEntity(value = value, stat = StatEntity(name = Stat.SP_ATK)),
        StatsEntity(value = value, stat = StatEntity(name = Stat.SP_DEF))
    )
}
