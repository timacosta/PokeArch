package com.architects.pokearch.core.framework.database.di

import android.content.Context
import androidx.room.Room
import com.architects.pokearch.core.framework.database.PokeArchDatabase
import com.architects.pokearch.core.framework.database.dao.PokemonDao
import com.architects.pokearch.core.framework.database.dao.PokemonInfoDao
import com.architects.pokearch.core.framework.database.entities.converters.TypesHolderConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val POKEARCH_DATABASE = "PokeArchDB"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePokeArchDatabase(
        @ApplicationContext appcontext: Context,
    ): PokeArchDatabase =
        Room.databaseBuilder(appcontext, PokeArchDatabase::class.java, POKEARCH_DATABASE)
            .build()

    @Provides
    @Singleton
    fun providePokemonDao(database: PokeArchDatabase): PokemonDao =
        database.pokemonDao()

    @Provides
    @Singleton
    fun providePokemonInfoDao(database: PokeArchDatabase): PokemonInfoDao =
        database.pokemonInfoDao()

    @Provides
    @Singleton
    fun provideTypeResponseConverter(): TypesHolderConverter = TypesHolderConverter()

}
