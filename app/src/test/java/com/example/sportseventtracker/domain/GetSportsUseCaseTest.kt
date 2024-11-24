package com.example.sportseventtracker.domain

import com.example.sportseventtracker.fakes.SportsFakes.mockExpectedAfterUseCase
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel1
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel2
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel3
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetSportsUseCaseTest {
    private val repository: SportsRepository = mockk()
    private val useCase = GetSportsUseCase(repository)

    @Test
    fun `invoke fetches sports data from repository`() = runTest {
        val sports = listOf(
            mockSportDomainModel1,
            mockSportDomainModel2,
            mockSportDomainModel3
        )
        coEvery { repository.getSports() } returns sports

        val result = useCase()

        assertEquals(mockExpectedAfterUseCase, result)
    }
}
