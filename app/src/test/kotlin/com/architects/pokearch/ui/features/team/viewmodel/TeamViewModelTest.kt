package com.architects.pokearch.ui.features.team.viewmodel

import app.cash.turbine.test
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.ui.features.team.state.TeamUiState
import com.architects.pokearch.usecases.GetPokemonTeam
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class TeamViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPokemonTeam = mockk<GetPokemonTeam>()

    @Test
    fun `GIVEN pokemon list WHEN initialized THEN uiState should be success with data`() = runTest {
        coEvery { getPokemonTeam() } answers {
            flowOf(
                listOf(
                    PokemonInfo(
                        id = 3502,
                        name = "Charmander",
                        height = 4532,
                        weight = 6780,
                        experience = 1634,
                        types = listOf(),
                        stats = listOf(),
                        team = false
                    ),
                    PokemonInfo(
                        id = 3503,
                        name = "Bulbasaur",
                        height = 4532,
                        weight = 6780,
                        experience = 1634,
                        types = listOf(),
                        stats = listOf(),
                        team = false
                    ),
                    PokemonInfo(
                        id = 3504,
                        name = "Squirtle",
                        height = 4532,
                        weight = 6780,
                        experience = 1634,
                        types = listOf(),
                        stats = listOf(),
                        team = false
                    ),
                )
            )
        }

        val viewModel = TeamViewModel(getPokemonTeam)

        viewModel.uiState.test {
            (awaitItem() as TeamUiState.Success).pokemonTeam.size shouldBeEqualTo 3
        }
    }

    @Test
    fun `GIVEN empty list WHEN initialized THEN uiState should be empty`() = runTest {
        coEvery { getPokemonTeam() } answers { emptyFlow() }

        val viewModel = TeamViewModel(getPokemonTeam)

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo TeamUiState.Success(emptyList())
        }
    }
}
