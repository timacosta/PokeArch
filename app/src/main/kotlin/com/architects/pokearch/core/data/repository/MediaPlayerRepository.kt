package com.architects.pokearch.core.data.repository

import com.architects.pokearch.core.data.datasource.MediaPlayerDataSource
import com.architects.pokearch.core.domain.repository.MediaPlayerRepositoryContract
import javax.inject.Inject

class MediaPlayerRepository @Inject constructor(
    private val mediaPlayerDataSource: MediaPlayerDataSource
): MediaPlayerRepositoryContract {
    override suspend fun playCry(url: String) =
        mediaPlayerDataSource.playCry(url)
}
