package com.architects.pokearch.core.data.datasource

interface MediaPlayerDataSource {
    suspend fun playCry(url: String)
}
