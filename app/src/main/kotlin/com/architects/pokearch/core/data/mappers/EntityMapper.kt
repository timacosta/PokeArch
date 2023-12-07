package com.architects.pokearch.core.data.mappers

interface EntityMapper<Network, Domain, Entity> {
    fun asEntity(network: Network): Entity
    fun asDomain(entity: Entity): Domain
}
