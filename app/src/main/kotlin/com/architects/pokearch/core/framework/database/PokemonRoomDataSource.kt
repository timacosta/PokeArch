package com.architects.pokearch.core.framework.database

import com.architects.pokearch.core.framework.database.dao.PokemonDao
import com.architects.pokearch.core.framework.database.dao.PokemonInfoDao
import com.architects.pokearch.core.framework.database.mapper.toDomain
import com.architects.pokearch.core.framework.database.mapper.toEntity
import com.architects.pokearch.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import javax.inject.Inject

class PokemonRoomDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao
) : PokemonLocalDataSource {
    override suspend fun getPokemonList(
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

    override suspend fun countPokemon(): Int = pokemonDao.numPokemonInDatabase()

    override suspend fun randomId(): Int = pokemonDao.randomId()
}
