package com.architects.pokearch.di

import com.architects.pokearch.core.framework.network.di.NetworkExtrasModule
import com.architects.pokearch.core.framework.network.di.annotations.CryApi
import com.architects.pokearch.core.framework.network.di.annotations.PokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


private const val Url = "http://localhost:8080/"

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkExtrasModule::class]
)
@Module
object FakeNetworkModule {
    @Provides
    @Singleton
    @PokeApi
    fun providePokeApiUrl(): String = Url

    @Provides
    @Singleton
    @CryApi
    fun provideCryApiUrl(): String = Url
}