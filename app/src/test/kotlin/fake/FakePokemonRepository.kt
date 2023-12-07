package fake

import com.architects.pokearch.core.data.model.NetworkPokemon


object FakePokemonRepository {
    fun getPokemons() = listOf(
        NetworkPokemon(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"),
        NetworkPokemon(name = "ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/"),
        NetworkPokemon(name = "venusaur", url = "https://pokeapi.co/api/v2/pokemon/3/"),
        NetworkPokemon(name = "charmander", url = "https://pokeapi.co/api/v2/pokemon/4/"),
        NetworkPokemon(name = "charmeleon", url = "https://pokeapi.co/api/v2/pokemon/5/"),
        NetworkPokemon(name = "charizard", url = "https://pokeapi.co/api/v2/pokemon/6/"),
        NetworkPokemon(name = "squirtle", url = "https://pokeapi.co/api/v2/pokemon/7/"),
        NetworkPokemon(name = "wartortle", url = "https://pokeapi.co/api/v2/pokemon/8/"),
        NetworkPokemon(name = "blastoise", url = "https://pokeapi.co/api/v2/pokemon/9/"),
        NetworkPokemon(name = "caterpie", url = "https://pokeapi.co/api/v2/pokemon/10/")
    )
}
