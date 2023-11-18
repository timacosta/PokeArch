package com.architects.pokearch.core.data.mappers

import com.architects.pokearch.core.data.database.entities.TypeEntity
import com.architects.pokearch.core.data.database.entities.TypesEntity
import com.architects.pokearch.core.data.database.entities.TypesHolder
import com.architects.pokearch.core.model.Type
import com.architects.pokearch.core.model.TypeResponse

object TypeEntityMapper : EntityMapper<List<TypeResponse>, TypesHolder>{
    override fun asEntity(domain: List<TypeResponse>): TypesHolder =
        TypesHolder(domain.map(::mapToTypesEntity))

    private fun mapToTypesEntity(typeResponse: TypeResponse) =
        TypesEntity(
            slot = typeResponse.slot,
            type = mapToTypeEntity(typeResponse.type)
        )

    private fun mapToTypeEntity(type: Type) =
        TypeEntity(name = type.name)

    override fun asDomain(entity: TypesHolder): List<TypeResponse> =
        entity.types.map(::mapToTypeResponse)

    private fun mapToTypeResponse(typesEntity: TypesEntity) =
        TypeResponse(
            slot = typesEntity.slot,
            type = mapToType(typesEntity.type)
        )

    private fun mapToType(typeEntity: TypeEntity) =
        Type(name = typeEntity.name)
}
