package com.architects.pokearch.data.repository

import com.architects.pokearch.data.datasource.MediaPlayerDataSource
import com.architects.pokearch.domain.repository.MediaPlayerRepositoryContract
import javax.inject.Inject

class MediaPlayerRepository @Inject constructor(
    private val mediaPlayerDataSource: MediaPlayerDataSource
) : MediaPlayerRepositoryContract {
    override suspend fun playCry(url: String) = mediaPlayerDataSource.playCry(url)
}
