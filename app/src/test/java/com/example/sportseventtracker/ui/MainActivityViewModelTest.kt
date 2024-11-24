package com.example.sportseventtracker.ui

import com.example.sportseventtracker.domain.GetSportsUseCase
import com.example.sportseventtracker.domain.MatchDomainModel
import com.example.sportseventtracker.domain.SportDomainModel
import com.example.sportseventtracker.domain.SportsRepository
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel1
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel2
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel3
import com.example.sportseventtracker.ui.model.MatchUiModel
import com.example.sportseventtracker.ui.model.SportUiModel
import com.example.sportseventtracker.ui.model.UiState
import com.example.sportseventtracker.utils.StringProvider
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    private val getSportsUseCase: GetSportsUseCase = mockk(relaxed = true)
    private val stringProvider: StringProvider = mockk(relaxed = true)
    private val repository: SportsRepository = mockk()

    private lateinit var viewModel: MainActivityViewModel

    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when data is fetched successfully should emits UiState Success`() = runTest {
        val sports = listOf(
            mockSportDomainModel1,
            mockSportDomainModel2,
            mockSportDomainModel3
        )

        coEvery { getSportsUseCase() } returns sports
        every { stringProvider.getString(any()) } returns "Error message"

        viewModel = MainActivityViewModel(
            getSportsUseCase,
            stringProvider,
            repository
        )


        dispatcher.scheduler.advanceTimeBy(1000L)

        val uiState = viewModel.uiState.value
        assertTrue(uiState is UiState.Success)
        assertEquals(sports.size, (uiState as UiState.Success).sports.size)
    }

    @Test
    fun `when use case fails should emits UiState Error`() = runTest {
        coEvery { getSportsUseCase() } throws Exception("API Error")
        every { stringProvider.getString(any()) } returns "Error message"

        viewModel = MainActivityViewModel(
            getSportsUseCase,
            stringProvider,
            repository
        )
        dispatcher.scheduler.advanceTimeBy(1000L)

        val uiState = viewModel.uiState.value
        assertTrue(uiState is UiState.Error)
        assertEquals("Error message", (uiState as UiState.Error).message)
    }

    @Test
    fun `setMatchFavourite updates match and calls database`() = runTest {
        val sportId = "sport1"
        val matchId = "match1"
        val matchUiModel = MatchUiModel(
            matchId = matchId,
            matchStartTime = 0L,
            timeLeft = "10:00",
            competitor1 = "Team A",
            competitor2 = "Team B",
            isFavourite = true
        )

        val sportDM = listOf(
            SportDomainModel(
                sportId = sportId,
                sportName = "Soccer",
                matchesList = listOf(
                    MatchDomainModel(
                        matchId = matchId,
                        matchName = "Team A - Team B",
                        matchStartTime = 0L,
                        isFavourite = false
                    )
                )
            )
        )

        coEvery { getSportsUseCase() } returns sportDM
        coEvery { repository.updateFavouriteInDb(any(), any(), any()) } just Runs

        viewModel = MainActivityViewModel(
            getSportsUseCase,
            stringProvider,
            repository
        )
        dispatcher.scheduler.advanceTimeBy(1000L)

        viewModel.setMatchFavourite(matchUiModel)

        val updatedState = viewModel.uiState.value as UiState.Success
        val updatedMatch = updatedState.sports.first().matches.first()

        assertEquals(true, updatedMatch.isFavourite) // Assert favourite updated
    }

    @Test
    fun `filterSports updates showFavoritesOnly for specific sport`() = runTest {
        val sportId = "sport1"
        val sports = listOf(
            SportUiModel(
                sportId = sportId,
                sportName = "Soccer",
                matches = listOf(),
                showFavoritesOnly = false
            )
        )

        val sportDM = listOf(
            SportDomainModel(
                sportId = sportId,
                sportName = "Soccer",
                matchesList = listOf()
            )
        )

        coEvery { getSportsUseCase() } returns sportDM

        viewModel = MainActivityViewModel(
            getSportsUseCase,
            stringProvider,
            repository
        )
        dispatcher.scheduler.advanceTimeBy(1000L)

        val updatedSport = sports.first().copy(showFavoritesOnly = true)
        viewModel.filterSports(updatedSport)

        val updatedState = viewModel.uiState.value as UiState.Success
        val filteredSport = updatedState.sports.first()

        assertEquals(true, filteredSport.showFavoritesOnly)
    }

}
