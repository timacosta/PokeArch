package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.database.entities.PokemonEntity
import com.architects.pokearch.core.model.Pokemon

object PokemonEntityMapper : EntityMapper<List<Pokemon>, List<PokemonEntity>> {
    override fun asEntity(domain: List<Pokemon>): List<PokemonEntity> =
        domain.map(::mapToPokemonEntity)

    private fun mapToPokemonEntity(pokemon: Pokemon) =
        PokemonEntity(
            id = pokemon.getIndex(),
            name = pokemon.name,
            url = pokemon.url
        )

    override fun asDomain(entity: List<PokemonEntity>): List<Pokemon> =
        entity.map(::mapToPokemon)

    private fun mapToPokemon(pokemonEntity: PokemonEntity) =
        Pokemon(
            name = pokemonEntity.name,
            url = pokemonEntity.url
        )
}
