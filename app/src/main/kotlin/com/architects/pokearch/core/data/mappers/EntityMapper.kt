package com.architects.pokearch.core.data.mappers

interface EntityMapper<Network, Domain, Entity> {
    fun asEntityFrom(network: Network): Entity
    fun asDomainFrom(entity: Entity): Domain
}
