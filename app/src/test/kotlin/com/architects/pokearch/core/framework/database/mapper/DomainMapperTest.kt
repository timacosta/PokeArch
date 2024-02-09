package com.architects.pokearch.core.framework.database.mapper

import com.architects.pokearch.samples.database.pokemonEntityListBuilder
import com.architects.pokearch.samples.database.pokemonInfoEntityBuilder
import com.architects.pokearch.samples.database.pokemonInfoEntityListBuilder
import com.architects.pokearch.samples.database.statsHolderBuilder
import com.architects.pokearch.testing.rules.samples.pokemonInfoBuilder
import com.architects.pokearch.testing.rules.samples.pokemonInfoListBuilder
import com.architects.pokearch.testing.rules.samples.pokemonListBuilder
import com.architects.pokearch.testing.rules.samples.statsBuilder
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class DomainMapperTest {

    @Test
    fun `GIVEN list of Pokemon WHEN toDomain THEN returns list of PokemonEntity`() {
        val pokemonList = pokemonListBuilder()
        val expectedPokemonEntityList = pokemonEntityListBuilder()

        val result = pokemonList.toEntity()

        result shouldBeEqualTo expectedPokemonEntityList
    }

    @Test
    fun `GIVEN PokemonInfo WHEN toDomain THEN returns PokemonInfoEntity`() {
        val pokemonInfo = pokemonInfoBuilder()
        val expectedPokemonInfoEntity = pokemonInfoEntityBuilder()

        val result = pokemonInfo.toEntity()

        result shouldBeEqualTo expectedPokemonInfoEntity
    }

    @Test
    fun `GIVEN list of PokemonInfo WHEN toDomain THEN returns list of PokemonInfoEntity`() {
        val pokemonInfoList = pokemonInfoListBuilder()
        val expectedPokemonInfoEntityList = pokemonInfoEntityListBuilder()

        val result = pokemonInfoList.map { it.toEntity() }

        result shouldBeEqualTo expectedPokemonInfoEntityList
    }

    @Test
    fun `GIVEN list of Stats WHEN toDomainStats THEN returns list of StatsHolder`() {
        val statsList = statsBuilder()
        val expectedStatsHolder = statsHolderBuilder()

        val result = statsList.toEntity()

        result shouldBeEqualTo expectedStatsHolder
    }

    @Test
    fun `GIVEN list of Types WHEN toDomainTypes THEN returns list of TypesHolder`() {
        val typesList = statsBuilder()
        val expectedTypesHolder = statsHolderBuilder()

        val result = typesList.toEntity()

        result shouldBeEqualTo expectedTypesHolder
    }
}
