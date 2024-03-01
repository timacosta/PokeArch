package com.architects.pokearch.core.framework.network.di

import android.content.Context
import android.net.ConnectivityManager
import com.architects.pokearch.core.framework.network.di.annotations.CryApi
import com.architects.pokearch.core.framework.network.di.annotations.CryRetrofit
import com.architects.pokearch.core.framework.network.di.annotations.PokeApi
import com.architects.pokearch.core.framework.network.di.annotations.PokeRetrofit
import com.architects.pokearch.core.framework.network.interceptor.ConnectivityInterceptor
import com.architects.pokearch.core.framework.network.service.CryService
import com.architects.pokearch.core.framework.network.service.PokedexService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideOKHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(RESPONSE_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(RESPONSE_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(provideConnectivityInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectivityInterceptor(connectivityManager)
    }

    @PokeApi
    @Provides
    @Singleton
    fun providePokeApiUrl(): String = BASE_POKE_API_URL

    @CryApi
    @Provides
    @Singleton
    fun provideCryApiUrl(): String = BASE_CRY_API_URL

    @PokeRetrofit
    @Provides
    @Singleton
    fun providePokeRetrofit(okHttpClient: OkHttpClient, @PokeApi baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @CryRetrofit
    @Provides
    @Singleton
    fun provideCryRetrofit(okHttpClient: OkHttpClient, @CryApi baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
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
