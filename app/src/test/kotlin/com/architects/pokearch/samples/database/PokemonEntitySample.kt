package com.architects.pokearch.samples.database

import com.architects.pokearch.core.framework.database.entities.PokemonEntity

fun pokemonEntityListBuilder(size: Int = 10): List<PokemonEntity> {
    var list = listOf<PokemonEntity>()
    for (i in 1..size) {
        list = list.plus(pokemonEntityBuilder(i))
    }
    return list
}

fun pokemonEntityBuilder(id: Int = 1) =
    PokemonEntity(
        id = id,
        name = "pokemon$id",
        url = "https://pokeapi.co/api/v2/pokemon/$id/"
    )
