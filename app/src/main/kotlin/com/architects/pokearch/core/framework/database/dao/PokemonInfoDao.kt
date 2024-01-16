package com.architects.pokearch.core.framework.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.architects.pokearch.core.framework.database.entities.PokemonInfoEntity

@Dao
interface PokemonInfoDao {

    //TODO: Change team = 1 when favorite is implemented
    @Query("SELECT * FROM PokemonInfoEntity WHERE team = 0 ORDER BY id ASC")
    suspend fun getPokemonTeam(): List<PokemonInfoEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfoEntity)

    @Query("SELECT * FROM PokemonInfoEntity WHERE id = :id")
    suspend fun getPokemonInfo(id: Int): PokemonInfoEntity?
}
