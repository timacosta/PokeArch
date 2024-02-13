package com.architects.pokearch.data.repository

import com.architects.pokearch.data.datasource.MediaPlayerDataSource
import com.architects.pokearch.testing.rules.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MediaPlayerRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN playCry WHEN invokes THEN call correct method from dataSource`() = runTest {
        val url = "http://example.com"
        val mediaPlayerDataSource: MediaPlayerDataSource = mockk(relaxed = true)
        val repository = buildRepository(mediaPlayerDataSource)

        repository.playCry(url)

        coVerify { mediaPlayerDataSource.playCry(url) }
    }

    private fun buildRepository(
        mediaPlayerDataSource: MediaPlayerDataSource,
    ) = MediaPlayerRepository(
        mediaPlayerDataSource = mediaPlayerDataSource
    )
}