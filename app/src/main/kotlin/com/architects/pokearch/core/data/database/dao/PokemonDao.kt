package com.architects.pokearch.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.architects.pokearch.core.data.database.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM PokemonEntity WHERE name LIKE :filter ORDER BY url ASC LIMIT :limit OFFSET :offset")
    fun getPokemonList(filter: String = "", limit: Int = 20, offset: Int = 0): Flow<List<PokemonEntity>>
}
