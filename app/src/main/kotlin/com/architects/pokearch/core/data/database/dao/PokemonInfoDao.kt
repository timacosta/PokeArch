package com.architects.pokearch.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.architects.pokearch.core.data.database.entities.PokemonInfoEntity

@Dao
interface PokemonInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfoEntity)

    @Query("SELECT * FROM PokemonInfoEntity WHERE id = :id")
    suspend fun getPokemonInfo(id: Int): PokemonInfoEntity?
}
