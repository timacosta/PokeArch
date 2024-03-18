package com.architects.pokearch.core.framework.network.mapper

import com.architects.pokearch.core.framework.network.mappers.toDomain
import com.architects.pokearch.core.framework.network.mappers.toDomainStats
import com.architects.pokearch.core.framework.network.mappers.toDomainTypes
import com.architects.pokearch.samples.network.networkPokemonInfoBuilder
import com.architects.pokearch.samples.network.networkPokemonInfoListBuilder
import com.architects.pokearch.samples.network.networkPokemonListBuilder
import com.architects.pokearch.samples.network.networkStatsBuilder
import com.architects.pokearch.samples.network.networkTypesBuilder
import com.architects.pokearch.testing.samples.pokemonInfoBuilder
import com.architects.pokearch.testing.samples.pokemonInfoListBuilder
import com.architects.pokearch.testing.samples.pokemonListBuilder
import com.architects.pokearch.testing.samples.statsBuilder
import com.architects.pokearch.testing.samples.typesBuilder
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NetworkMappersTest {

    @Test
    fun `GIVEN List of NetworkPokemon WHEN toDomain THEN returns List of Pokemon`() {
        val networkPokemon = networkPokemonListBuilder()
        val expectedPokemon = pokemonListBuilder()

        val result = networkPokemon.toDomain()

        result shouldBeEqualTo expectedPokemon
    }

    @Test
    fun `GIVEN NetworkPokemonInfo WHEN toDomain THEN returns PokemonInfo`() {
        val networkPokemonInfo = networkPokemonInfoBuilder()
        val expectedPokemonInfo = pokemonInfoBuilder()

        val result = networkPokemonInfo.toDomain()

        result shouldBeEqualTo expectedPokemonInfo
    }

    @Test
    fun `GIVEN list of NetworkPokemonInfo WHEN toDomain THEN returns list of PokemonInfo`() {
        val networkPokemonInfo = networkPokemonInfoListBuilder()
        val expectedPokemonInfo = pokemonInfoListBuilder()

        val result = networkPokemonInfo.map { it.toDomain() }

        result shouldBeEqualTo expectedPokemonInfo
    }

    @Test
    fun `GIVEN list of NetworkTypes WHEN toDomainTypes THEN returns list of Types`() {
        val networkTypes = networkTypesBuilder()
        val expectedType = typesBuilder()

        val result = networkTypes.toDomainTypes()

        result shouldBeEqualTo expectedType
    }

    @Test
    fun `GIVEN list of NetworkStats WHEN toDomainStats THEN returns list of Stats`() {
        val networkStats = networkStatsBuilder()
        val expectedStats = statsBuilder()

        val result = networkStats.toDomainStats()

        result shouldBeEqualTo expectedStats
    }
}
