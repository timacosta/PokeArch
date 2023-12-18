package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.local.database.entities.PokemonInfoEntity
import com.architects.pokearch.core.data.network.model.NetworkPokemonInfo
import com.architects.pokearch.core.domain.model.PokemonInfo

object PokemonInfoEntityMapper : EntityMapper<NetworkPokemonInfo, PokemonInfo, PokemonInfoEntity> {
    override fun asEntityFrom(network: NetworkPokemonInfo) =
        PokemonInfoEntity(
            id = network.id,
            name = network.name,
            height = network.height,
            weight = network.weight,
            experience = network.experience,
            types = TypeEntityMapper.asEntityFrom(network.types),
            stats = StatEntityMapper.asEntityFrom(network.stats),
            team = network.team,
        )

    override fun asDomainFrom(entity: PokemonInfoEntity) =
        PokemonInfo(
            id = entity.id,
            name = entity.name.replaceFirstChar { it.uppercaseChar() },
            height = entity.height,
            weight = entity.weight,
            experience = entity.experience,
            types = TypeEntityMapper.asDomainFrom(entity.types),
            stats = StatEntityMapper.asDomainFrom(entity.stats),
            team = entity.team,
        )
}
