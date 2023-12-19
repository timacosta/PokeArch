package com.architects.pokearch.core.framework.local

import com.architects.pokearch.core.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.core.domain.mappers.toEntity
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.framework.local.dao.PokemonDao
import com.architects.pokearch.core.framework.local.dao.PokemonInfoDao
import com.architects.pokearch.core.framework.local.mapper.toDomain
import javax.inject.Inject

class PokemonRoomDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao
) : PokemonLocalDataSource {
    override suspend fun getPokemonListFromDatabase(
        filter: String,
        limit: Int,
        offset: Int
    ): List<Pokemon> {

        return pokemonDao.getPokemonList(filter, limit, offset).toDomain()
    }

    override suspend fun savePokemonList(pokemonList: List<Pokemon>) {
        pokemonDao.insertPokemonList(pokemonList.toEntity())
    }

    override suspend fun savePokemonInfo(pokemonInfo: PokemonInfo) {
        pokemonInfoDao.insertPokemonInfo(pokemonInfo.toEntity())
    }

    override suspend fun getPokemonInfo(id: Int): PokemonInfo? {

        return pokemonInfoDao.getPokemonInfo(id)?.toDomain()
    }

    override suspend fun numPokemonInDatabase(): Int = pokemonDao.numPokemonInDatabase()

    override suspend fun randomId(): Int = pokemonDao.randomId()
}
