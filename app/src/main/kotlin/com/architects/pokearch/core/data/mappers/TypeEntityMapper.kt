package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.local.database.entities.converters.TypeEntity
import com.architects.pokearch.core.data.local.database.entities.converters.TypesEntity
import com.architects.pokearch.core.data.local.database.entities.converters.TypesHolder
import com.architects.pokearch.core.data.network.model.NetworkType
import com.architects.pokearch.core.data.network.model.NetworkTypes
import com.architects.pokearch.core.domain.model.Type
import com.architects.pokearch.core.domain.model.Types

object TypeEntityMapper : EntityMapper<List<NetworkTypes>, List<Types>, TypesHolder>{
    override fun asEntity(domain: List<NetworkTypes>): TypesHolder =
        TypesHolder(domain.map(::mapToTypesEntity))

    private fun mapToTypesEntity(typeResponse: NetworkTypes) =
        TypesEntity(
            slot = typeResponse.slot,
            type = mapToTypeEntity(typeResponse.networkType)
        )

    private fun mapToTypeEntity(networkType: NetworkType) =
        TypeEntity(name = networkType.name)

    override fun asDomain(entity: TypesHolder): List<Types> =
        entity.types.map(this::mapToTypes)

    private fun mapToTypes(typesEntity: TypesEntity) =
        Types(
            slot = typesEntity.slot,
            type = mapToType(typesEntity.type)
        )

    private fun mapToType(typeEntity: TypeEntity) =
        Type(name = typeEntity.name.replaceFirstChar { it.uppercaseChar() })
}
