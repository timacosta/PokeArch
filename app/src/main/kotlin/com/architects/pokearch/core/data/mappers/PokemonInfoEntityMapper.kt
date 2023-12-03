package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.database.entities.PokemonInfoEntity
import com.architects.pokearch.core.model.PokemonInfo

object PokemonInfoEntityMapper : EntityMapper<PokemonInfo, PokemonInfoEntity> {
    override fun asEntity(domain: PokemonInfo) =
        PokemonInfoEntity(
            id = domain.id,
            name = domain.name,
            height = domain.height,
            weight = domain.weight,
            experience = domain.experience,
            types = TypeEntityMapper.asEntity(domain.types),
            stats = StatEntityMapper.asEntity(domain.stats),
            team = domain.team,
        )

    override fun asDomain(entity: PokemonInfoEntity) =
        PokemonInfo(
            id = entity.id,
            name = entity.name,
            height = entity.height,
            weight = entity.weight,
            experience = entity.experience,
            types = TypeEntityMapper.asDomain(entity.types),
            stats = StatEntityMapper.asDomain(entity.stats),
            team = entity.team,
        )

}
