package com.architects.pokearch.core.framework.database.mapper

import com.architects.pokearch.samples.database.pokemonEntityBuilder
import com.architects.pokearch.samples.database.pokemonEntityListBuilder
import com.architects.pokearch.samples.database.pokemonInfoEntityBuilder
import com.architects.pokearch.samples.database.pokemonInfoEntityListBuilder
import com.architects.pokearch.samples.database.statsHolderBuilder
import com.architects.pokearch.samples.database.typesHolderBuilder
import com.architects.pokearch.testing.samples.pokemonBuilder
import com.architects.pokearch.testing.samples.pokemonInfoBuilder
import com.architects.pokearch.testing.samples.pokemonInfoListBuilder
import com.architects.pokearch.testing.samples.pokemonListBuilder
import com.architects.pokearch.testing.samples.statsBuilder
import com.architects.pokearch.testing.samples.typesBuilder
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class RealEntityMapperTest {

    @Test
    fun `GIVEN PokemonEntity WHEN toDomain THEN returns Pokemon`() {
        val pokemonEntity = pokemonEntityBuilder()
        val expectedPokemon = pokemonBuilder()

        val result = pokemonEntity.toDomain()

        result shouldBeEqualTo expectedPokemon
    }

    @Test
    fun `GIVEN list of PokemonEntity WHEN toDomain THEN returns list of Pokemon`() {
        val pokemonEntityList = pokemonEntityListBuilder()
        val expectedPokemonList = pokemonListBuilder()

        val result = pokemonEntityList.toDomain()

        result shouldBeEqualTo expectedPokemonList
    }

    @Test
    fun `GIVEN PokemonInfoEntity WHEN toDomain THEN returns PokemonInfo`() {
        val pokemonInfoEntity = pokemonInfoEntityBuilder()
        val expectedPokemonInfo = pokemonInfoBuilder()

        val result = pokemonInfoEntity.toDomain()

        result shouldBeEqualTo expectedPokemonInfo
    }

    @Test
    fun `GIVEN list of PokemonInfoEntity WHEN toDomain THEN returns list of PokemonInfo`() {
        val pokemonInfoEntityList = pokemonInfoEntityListBuilder()
        val expectedPokemonInfoList = pokemonInfoListBuilder()

        val result = pokemonInfoEntityList.map { it.toDomain() }

        result shouldBeEqualTo expectedPokemonInfoList
    }

    @Test
    fun `GIVEN TypesHolder WHEN toDomain THEN returns list of Types`() {
        val typesHolder = typesHolderBuilder()
        val expectedTypeList = typesBuilder()

        val result = typesHolder.toDomain()

        result shouldBeEqualTo expectedTypeList
    }

    @Test
    fun `GIVEN StatsHolder WHEN toDomain THEN returns list of Stats`() {
        val statsHolder = statsHolderBuilder()
        val expectedStatsList = statsBuilder()

        val result = statsHolder.toDomain()

        result shouldBeEqualTo expectedStatsList
    }
}
