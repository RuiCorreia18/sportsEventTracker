package com.example.sportseventtracker.ui

import androidx.lifecycle.ViewModel
import com.example.sportseventtracker.domain.SportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: SportsRepository
): ViewModel() {
    private val _sports = MutableStateFlow(generateDummySports())
    val sports: StateFlow<List<SportsUiModel>> = _sports

    init {
        loadSportsData()
    }

    private fun loadSportsData() {
        val dummySports = generateDummySports()
        _sports.value = dummySports
    }

    private fun generateDummySports(): List<SportsUiModel> {
        return List(5) { sportIndex ->
            SportsUiModel(
                sportId = "Id $sportIndex",
                sportName = "Sport $sportIndex",
                isFavourite = false,
                matches = List(4) { matchIndex ->
                    MatchUiModel(
                        matchId = matchIndex.toString(),
                        timeLeft = "00:11:22",
                        competitor1 = "Cpmpetitor 1",
                        competitor2 = "Cpmpetitor 2",
                        isFavourite = false
                    )
                },
            )
        }
    }
}
