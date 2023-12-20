package com.architects.pokearch.core.framework.network.di

import com.architects.pokearch.core.framework.network.di.annotations.CryRetrofit
import com.architects.pokearch.core.framework.network.di.annotations.PokeRetrofit
import com.architects.pokearch.core.framework.network.service.CryService
import com.architects.pokearch.core.framework.network.service.PokedexService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_POKE_API_URL = "https://pokeapi.co/api/v2/"
private const val BASE_CRY_API_URL = "https://play.pokemonshowdown.com/audio/cries/"
private const val RESPONSE_TIME_OUT_SECONDS: Long = 100
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(RESPONSE_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(RESPONSE_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @PokeRetrofit
    @Provides
    @Singleton
    fun providePokeRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_POKE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @CryRetrofit
    @Provides
    @Singleton
    fun provideCryRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_CRY_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokedexService(@PokeRetrofit retrofit: Retrofit): PokedexService {
        return retrofit.create(PokedexService::class.java)
    }

    @Provides
    @Singleton
    fun provideCryService(@CryRetrofit retrofit: Retrofit): CryService {
        return retrofit.create(CryService::class.java)
    }
}