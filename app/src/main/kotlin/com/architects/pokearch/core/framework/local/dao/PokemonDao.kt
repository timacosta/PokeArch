package com.architects.pokearch.core.framework.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.architects.pokearch.core.framework.local.entities.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonEntity>)

    @Query(
        "SELECT * FROM PokemonEntity " +
                "WHERE name LIKE '%' || :filter || '%' " +
                "ORDER BY id ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getPokemonList(
        filter: String = "",
        limit: Int = 20,
        offset: Int = 0
    ): List<PokemonEntity>

    @Query("SELECT COUNT(*) FROM PokemonEntity")
    suspend fun numPokemonInDatabase(): Int

    @Query("SELECT id FROM PokemonEntity ORDER BY RANDOM() LIMIT 1")
    suspend fun randomId(): Int
}
