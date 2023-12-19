package com.architects.pokearch.core.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.architects.pokearch.core.framework.database.dao.PokemonDao
import com.architects.pokearch.core.framework.database.dao.PokemonInfoDao
import com.architects.pokearch.core.framework.database.entities.PokemonEntity
import com.architects.pokearch.core.framework.database.entities.PokemonInfoEntity
import com.architects.pokearch.core.framework.database.entities.converters.StatsHolderConverter
import com.architects.pokearch.core.framework.database.entities.converters.TypesHolderConverter

@Database(
    entities = [PokemonEntity::class, PokemonInfoEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(TypesHolderConverter::class, StatsHolderConverter::class)
abstract class PokeArchDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonInfoDao(): PokemonInfoDao
}
