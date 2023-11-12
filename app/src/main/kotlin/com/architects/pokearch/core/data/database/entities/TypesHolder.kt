package com.architects.pokearch.core.data.database.entities

import com.architects.pokearch.core.model.Type
import com.architects.pokearch.core.model.TypeResponse

data class TypesHolder (
    val types: List<TypesEntity>
)

data class TypesEntity(
    val slot: Int,
    val type: TypeEntity
)

fun TypeResponse.asTypesEntity() = TypesEntity(slot, type.asTypeEntity())

data class TypeEntity(
    val name: String
)

fun Type.asTypeEntity() = TypeEntity(name)
