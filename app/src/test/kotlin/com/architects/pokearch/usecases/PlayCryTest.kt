package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.MediaPlayerRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

class PlayCryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN PlayCryUseCase WHEN invokes THEN calls repository playCry and returns expected result`() =
        runTest {
            val repository: MediaPlayerRepositoryContract = mockk {
                coEvery { playCry(any()) } returns Unit
            }
            val useCase = PlayCry(repository)

            val result = useCase.invoke("url")
            val expectedResult = Unit

            coVerifyOnce { repository.playCry(any()) }
            result shouldBe expectedResult
        }
}
