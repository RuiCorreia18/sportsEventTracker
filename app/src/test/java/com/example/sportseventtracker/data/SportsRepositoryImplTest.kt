package com.example.sportseventtracker.data

import com.example.sportseventtracker.data.local.FavoriteDao
import com.example.sportseventtracker.data.local.FavoriteEntity
import com.example.sportseventtracker.fakes.FavoriteEntityFakes.mockFavoriteEntity1
import com.example.sportseventtracker.fakes.FavoriteEntityFakes.mockFavoriteEntity2
import com.example.sportseventtracker.fakes.SportResponseFakes.mockSportResponse1
import com.example.sportseventtracker.fakes.SportResponseFakes.mockSportResponse2
import com.example.sportseventtracker.fakes.SportResponseFakes.mockSportResponse3
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel1
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel2
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel3
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SportsRepositoryImplTest {
    private val api: SportsApi = mockk()
    private val dao: FavoriteDao = mockk(relaxed = true)
    private val repository = SportsRepositoryImpl(api, dao)

    @Test
    fun `getSports combines API and favorites data correctly`() = runTest {
        val apiResponse = listOf(
            mockSportResponse1,
            mockSportResponse2,
            mockSportResponse3
        )
        val favorites = listOf(
            mockFavoriteEntity1,
            mockFavoriteEntity2
        )
        coEvery { api.getSports() } returns apiResponse
        coEvery { dao.getAll() } returns favorites

        val result = repository.getSports()
        val expected = listOf(
            mockSportDomainModel1,
            mockSportDomainModel2,
            mockSportDomainModel3,
        )

        assertEquals(expected, result)
    }

    @Test
    fun `updateFavouriteInDb inserts correct entity`() = runTest {
        val sportId = "sport1"
        val matchId = "match1"
        val isFavourite = true

        repository.updateFavouriteInDb(sportId, matchId, isFavourite)

        val slot = slot<FavoriteEntity>()
        coVerify { dao.insert(capture(slot)) }

        val capturedEntity = slot.captured
        assert(capturedEntity.sportId == sportId)
        assert(capturedEntity.matchId == matchId)
        assert(capturedEntity.isFavourite == isFavourite)
    }
}
