package com.architects.pokearch.core.framework.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.architects.pokearch.core.framework.database.entities.PokemonInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonInfoDao {

    @Query("SELECT * FROM PokemonInfoEntity WHERE team = 1 ORDER BY id ASC")
    fun getPokemonTeam(): Flow<List<PokemonInfoEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfoEntity)

    @Query("SELECT * FROM PokemonInfoEntity WHERE id = :id")
    suspend fun getPokemonInfo(id: Int): PokemonInfoEntity?
}
