package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.database.entities.PokemonEntity
import com.architects.pokearch.core.data.model.NetworkPokemon
import com.architects.pokearch.core.domain.model.Pokemon

object PokemonEntityMapper : EntityMapper<List<NetworkPokemon>, List<Pokemon>, List<PokemonEntity>> {
    override fun asEntity(network: List<NetworkPokemon>): List<PokemonEntity> =
        network.map(::mapToPokemonEntity)

    private fun mapToPokemonEntity(networkPokemon: NetworkPokemon) =
        PokemonEntity(
            id = networkPokemon.getIndex(),
            name = networkPokemon.name,
            url = networkPokemon.url
        )

    override fun asDomain(entity: List<PokemonEntity>): List<Pokemon> =
        entity.map(::mapToPokemon)

    private fun mapToPokemon(pokemonEntity: PokemonEntity) =
        Pokemon(
            name = pokemonEntity.name,
            url = pokemonEntity.url
        )
}
