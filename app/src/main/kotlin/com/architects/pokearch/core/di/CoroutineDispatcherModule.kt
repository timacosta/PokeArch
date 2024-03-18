package com.architects.pokearch.core.di

import com.architects.pokearch.core.di.annotations.IO
import com.architects.pokearch.core.di.annotations.Main
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Suppress("InjectDispatcher")
@InstallIn(SingletonComponent::class)
@Module
object CoroutineDispatcherModule {

    @Main
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @IO
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
