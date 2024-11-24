package com.example.sportseventtracker.data

import com.example.sportseventtracker.fakes.SportResponseFakes.mockSportResponse1
import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel1
import org.junit.Assert.assertEquals
import org.junit.Test

class SportMapperTest {

    @Test
    fun `SportResponse toDomainModel maps correctly`() {
        val domainModel = mockSportResponse1.toDomainModel()

        val expected = mockSportDomainModel1.copy(
            matchesList = mockSportDomainModel1.matchesList.map { match ->
                match.copy(isFavourite = false)
            }
        )

        assertEquals(expected, domainModel)
    }
}
