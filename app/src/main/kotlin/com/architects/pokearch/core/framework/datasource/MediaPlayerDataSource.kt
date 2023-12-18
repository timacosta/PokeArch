package com.architects.pokearch.core.framework.datasource

interface MediaPlayerDataSource {
    suspend fun playCry(url: String)
}
