package com.architects.pokearch.data.datasource

interface MediaPlayerDataSource {
    suspend fun playCry(url: String)
}
