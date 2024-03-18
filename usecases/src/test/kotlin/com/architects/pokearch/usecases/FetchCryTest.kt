package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

class FetchCryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN FetchCryUseCase WHEN invokes THEN calls repository fetchCry and returns expected result`() = runTest {
        val expectedResult = "ditto cry"
        val repository: PokeArchRepositoryContract = mockk {
            coEvery { fetchCry(any()) } returns expectedResult
        }
        val useCase = FetchCry(repository)

        val result = useCase("ditto")

        coVerifyOnce { repository.fetchCry(any()) }
        result shouldBe expectedResult
    }
}
