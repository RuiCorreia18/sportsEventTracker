package com.example.sportseventtracker.ui

import com.example.sportseventtracker.fakes.SportsFakes.mockSportDomainModel1
import com.example.sportseventtracker.fakes.SportsFakes.mockSportUiModel1
import com.example.sportseventtracker.ui.mapper.toUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class DomainToUiMapperTest {

    @Test
    fun `SportDomainModel toUiModel maps correctly`() {
        val uiModel = mockSportDomainModel1.toUiModel()

        assertEquals(mockSportUiModel1, uiModel)
    }
}
