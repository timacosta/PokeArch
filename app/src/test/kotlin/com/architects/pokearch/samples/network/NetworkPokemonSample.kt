package com.architects.pokearch.samples.network

import com.architects.pokearch.core.framework.network.model.NetworkPokemon


fun networkPokemonListBuilder(size: Int = 10): List<NetworkPokemon> {
    var list = listOf<NetworkPokemon>()
    for (i in 1..size) {
        list = list.plus(networkPokemonBuilder(i))
    }
    return list
}

fun networkPokemonBuilder(id: Int = 1) =
    NetworkPokemon(
        name = "pokemon$id",
        url = "https://pokeapi.co/api/v2/pokemon/$id/"
    )
