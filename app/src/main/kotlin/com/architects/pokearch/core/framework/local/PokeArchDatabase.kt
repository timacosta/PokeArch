package com.architects.pokearch.core.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.architects.pokearch.core.framework.local.dao.PokemonDao
import com.architects.pokearch.core.framework.local.dao.PokemonInfoDao
import com.architects.pokearch.core.framework.local.entities.PokemonEntity
import com.architects.pokearch.core.framework.local.entities.PokemonInfoEntity
import com.architects.pokearch.core.framework.local.entities.converters.StatsHolderConverter
import com.architects.pokearch.core.framework.local.entities.converters.TypesHolderConverter

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
