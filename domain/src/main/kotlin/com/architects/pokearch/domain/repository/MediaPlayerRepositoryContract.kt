package com.architects.pokearch.domain.repository

interface MediaPlayerRepositoryContract {
    suspend fun playCry(url: String)
}
