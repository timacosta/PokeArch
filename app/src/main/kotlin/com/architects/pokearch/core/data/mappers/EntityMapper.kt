package com.architects.pokearch.core.data.mappers

interface EntityMapper<Domain, Entity> {
    fun asEntity(domain: Domain): Entity
    fun asDomain(entity: Entity): Domain
}
