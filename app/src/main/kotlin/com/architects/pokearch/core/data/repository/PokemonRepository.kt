package com.architects.pokearch.core.data.repository

import com.architects.pokearch.core.model.Pokemon

object PokemonRepository {
    fun getPokemons() = listOf(
        Pokemon(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"),
        Pokemon(name = "ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/"),
        Pokemon(name = "venusaur", url = "https://pokeapi.co/api/v2/pokemon/3/"),
        Pokemon(name = "charmander", url = "https://pokeapi.co/api/v2/pokemon/4/"),
        Pokemon(name = "charmeleon", url = "https://pokeapi.co/api/v2/pokemon/5/"),
        Pokemon(name = "charizard", url = "https://pokeapi.co/api/v2/pokemon/6/"),
        Pokemon(name = "squirtle", url = "https://pokeapi.co/api/v2/pokemon/7/"),
        Pokemon(name = "wartortle", url = "https://pokeapi.co/api/v2/pokemon/8/"),
        Pokemon(name = "blastoise", url = "https://pokeapi.co/api/v2/pokemon/9/"),
        Pokemon(name = "caterpie", url = "https://pokeapi.co/api/v2/pokemon/10/")
    )
}
