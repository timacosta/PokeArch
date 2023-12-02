package com.architects.pokearch.core.domain.repository

interface MediaPlayerRepositoryContract {
    suspend fun playCry(url: String)
}
