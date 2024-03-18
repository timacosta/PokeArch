package com.architects.pokearch.di

import android.content.Context
import androidx.room.Room
import com.architects.pokearch.core.framework.database.PokeArchDatabase
import com.architects.pokearch.core.framework.database.dao.PokemonDao
import com.architects.pokearch.core.framework.database.dao.PokemonInfoDao
import com.architects.pokearch.core.framework.database.di.DatabaseModule
import com.architects.pokearch.core.framework.database.entities.converters.TypesHolderConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
@Module
object FakeDatabaseModule {
    @Provides
    @Singleton
    fun providePokeArchDatabase(
        @ApplicationContext appcontext: Context,
    ): PokeArchDatabase =
        Room
            .inMemoryDatabaseBuilder(appcontext, PokeArchDatabase::class.java)
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