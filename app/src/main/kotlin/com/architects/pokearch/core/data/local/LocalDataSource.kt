package com.architects.pokearch.core.data.local

import com.architects.pokearch.core.data.local.database.dao.PokemonDao
import com.architects.pokearch.core.data.local.database.dao.PokemonInfoDao
import com.architects.pokearch.core.data.local.database.entities.PokemonEntity
import com.architects.pokearch.core.data.local.database.entities.PokemonInfoEntity
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
    ): List<PokemonEntity> {

        return pokemonDao.getPokemonList(filter, limit, offset)
    }

    suspend fun savePokemonList(pokemonList: List<Pokemon>) {
        pokemonDao.insertPokemonList(
            pokemonList.toEntity()
        )
    }

    suspend fun savePokemonInfo(pokemonInfo: PokemonInfo) {
        pokemonInfoDao.insertPokemonInfo(
            pokemonInfo.toEntity()
        )
    }

    suspend fun getPokemonInfo(id: Int): PokemonInfoEntity? {

        return pokemonInfoDao.getPokemonInfo(id)
    }

    suspend fun numPokemonInDatabase(): Int = pokemonDao.numPokemonInDatabase()

    suspend fun randomId(): Int = pokemonDao.randomId()
}
