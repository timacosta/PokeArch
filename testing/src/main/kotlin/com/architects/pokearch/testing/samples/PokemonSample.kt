package com.architects.pokearch.testing.samples

import com.architects.pokearch.domain.model.Pokemon

fun pokemonListBuilder(size: Int = 10): List<Pokemon> {
    var list = listOf<Pokemon>()
    for (i in 1..size) {
        list = list.plus(pokemonBuilder(i))
    }
    return list
}

fun pokemonBuilder(id: Int = 1) =
    Pokemon(
        name = "pokemon$id",
        url = "https://pokeapi.co/api/v2/pokemon/$id/",
    )
