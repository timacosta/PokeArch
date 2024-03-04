package com.architects.pokearch.ui.features.shakeNCatch.viewModel

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.error.ErrorType
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonInfoBuilder
import com.architects.pokearch.testing.verifications.coVerifyNever
import com.architects.pokearch.testing.verifications.coVerifyOnce
import com.architects.pokearch.testing.verifications.verifyNever
import com.architects.pokearch.testing.verifications.verifyOnce
import com.architects.pokearch.ui.features.shakeNCatch.state.ShakeNCatchUiState
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.GetAccelerometerValue
import com.architects.pokearch.usecases.GetRandomPokemon
import com.architects.pokearch.usecases.Vibrate
import io.mockk.coEvery
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShakeNCatchViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAccelerometerValue: GetAccelerometerValue = mockk()
    private val getRandomPokemon: GetRandomPokemon = mockk()
    private val vibrate: Vibrate = mockk()
    private val errorDialogManager: ErrorDialogManager = ErrorDialogManager()

    private lateinit var viewModel: ShakeNCatchViewModel

    @Before
    fun setUp() {
        every { getAccelerometerValue() } returns buildAccelerationFlow()
        coEvery { getRandomPokemon() } returns flowOf(pokemonInfoBuilder().right())
        justRun { vibrate() }
    }

    @Test
    fun `GIVEN accelerometer value is 0 WHEN init THEN states no change`() = runTest {
        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState()
        }
        verifyOnce { getAccelerometerValue() }
        coVerifyNever { getRandomPokemon() }
        verifyNever { vibrate() }
        viewModel.dialogState.value shouldBeEqualTo null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN accelerometer value is greater than 8 and greater than or equal to -8 after WHEN init THEN states no change`() = runTest {
        every { getAccelerometerValue() } returns buildAccelerationFlow(9f, -8f)

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState()
        }
        advanceUntilIdle()
        verifyOnce { getAccelerometerValue() }
        coVerifyNever { getRandomPokemon() }
        verifyNever { vibrate() }
        viewModel.dialogState.value shouldBeEqualTo null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN accelerometer value is less than or equal to 8 and less than -8 after WHEN init THEN states no change`() = runTest {
        every { getAccelerometerValue() } returns buildAccelerationFlow(8f, -9f)

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState()
        }
        advanceUntilIdle()
        verifyOnce { getAccelerometerValue() }
        coVerifyNever { getRandomPokemon() }
        verifyNever { vibrate() }
        viewModel.dialogState.value shouldBeEqualTo null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN accelerometer value is greater than 8 and less than -8 after WHEN init with success THEN get random pokemon`() = runTest {
        every { getAccelerometerValue() } returns buildAccelerationFlow(9f, -9f)

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState()
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(acceleration = 9f)
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(acceleration = -9f)
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(
                openedPokeball = true,
                acceleration = -9f,
                pokemonInfo = pokemonInfoBuilder()
            )
        }
        advanceUntilIdle()
        verifyOnce { getAccelerometerValue() }
        coVerifyOnce { getRandomPokemon() }
        verifyOnce { vibrate() }
        viewModel.dialogState.value shouldBeEqualTo null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN accelerometer value is greater than 8 and less than -8 after WHEN init with error THEN get failure`() = runTest {
        val expectedErrorTitle = R.string.error_title_no_internet
        val expectedErrorMessage = R.string.error_message_no_internet
        every { getAccelerometerValue() } returns buildAccelerationFlow(9f, -9f)
        coEvery { getRandomPokemon() } returns flowOf(Failure.NetworkError(ErrorType.NoInternet).left())

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState()
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(acceleration = 9f)
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(acceleration = -9f)
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(
                openedPokeball = true,
                acceleration = -9f,
                error = true
            )
        }
        advanceUntilIdle()
        verifyOnce { getAccelerometerValue() }
        coVerifyOnce { getRandomPokemon() }
        verifyOnce { vibrate() }
        viewModel.dialogState.value?.title shouldBeEqualTo expectedErrorTitle
        viewModel.dialogState.value?.message shouldBeEqualTo expectedErrorMessage
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN accelerometer value is 9 and -9 after WHEN afterNavigation THEN onDetail is true and accelerationMinMax is 0`() = runTest {
        every { getAccelerometerValue() } returns buildAccelerationFlow(9f, -9f)
        viewModel = buildViewModel()
        advanceUntilIdle()

        viewModel.afterNavigation()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(onDetail = true)
        }
        viewModel.accelerationMin shouldBeEqualTo 0f
        viewModel.accelerationMax shouldBeEqualTo 0f
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN onDetail is true WHEN backFromDetail THEN onDetail is false`() = runTest {
        every { getAccelerometerValue() } returns buildAccelerationFlow(9f, -9f)
        viewModel = buildViewModel()
        viewModel.afterNavigation()
        advanceUntilIdle()

        viewModel.backFromDetail()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  ShakeNCatchUiState(onDetail = false)
        }
    }

    private fun buildViewModel() = ShakeNCatchViewModel(
            getAccelerometerValue = getAccelerometerValue,
            getRandomPokemon = getRandomPokemon,
            vibrate = vibrate,
            dispatcher = mainDispatcherRule.testDispatcher,
            errorDialogManager = errorDialogManager
        )

    private fun buildAccelerationFlow(firstChange: Float = 0f, secondChange: Float = 0f) = flow {
        emit(0f)
        delay(100)
        emit(firstChange)
        delay(100)
        emit(secondChange)
    }
}
