package com.architects.pokearch.core.data.database.entities

data class TypesHolder (
    val types: List<TypesEntity>
)

data class TypesEntity(
    val slot: Int,
    val type: TypeEntity
)

data class TypeEntity(
    val name: String
)
