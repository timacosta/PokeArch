package com.architects.pokearch.core.data.local

import com.architects.pokearch.core.data.local.database.dao.PokemonDao
import com.architects.pokearch.core.data.local.database.dao.PokemonInfoDao
import com.architects.pokearch.core.data.local.database.mapper.toDomain
import com.architects.pokearch.core.domain.mappers.toEntity
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao
) {
    suspend fun getPokemonListFromDatabase(
        filter: String,
        limit: Int,
        offset: Int
    ): List<Pokemon> {

        val pokemonList = pokemonDao.getPokemonList(filter, limit, offset)

        return pokemonList.toDomain()
    }

    suspend fun save(pokemonList: List<Pokemon>) {
        pokemonDao.insertPokemonList(
            pokemonList.toEntity()
        )
    }

    suspend fun numPokemonInDatabase(): Int = pokemonDao.numPokemonInDatabase()

    suspend fun randomId(): Int = pokemonDao.randomId()

    suspend fun getPokemonInfo(id: Int): PokemonInfo? {
        val pokemonInfoEntity = pokemonInfoDao.getPokemonInfo(id)

        return pokemonInfoEntity?.toDomain()
    }
}
