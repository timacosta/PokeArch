package com.architects.pokearch.core.framework.mappers

interface EntityMapper<Network, Domain, Entity> {
    fun asEntityFrom(network: Network): Entity
    fun asDomainFrom(entity: Entity): Domain
}
