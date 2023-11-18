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
            team = domain.team,
            hp = domain.hp,
            attack = domain.attack,
            defense = domain.defense,
            speed = domain.speed,
            exp = domain.exp,
        )

    override fun asDomain(entity: PokemonInfoEntity) =
        PokemonInfo(
            id = entity.id,
            name = entity.name,
            height = entity.height,
            weight = entity.weight,
            experience = entity.experience,
            types = TypeEntityMapper.asDomain(entity.types),
            team = entity.team,
            hp = entity.hp,
            attack = entity.attack,
            defense = entity.defense,
            speed = entity.speed,
            exp = entity.exp
        )

}
