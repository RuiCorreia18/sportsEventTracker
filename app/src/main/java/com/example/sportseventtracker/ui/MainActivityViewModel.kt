package com.example.sportseventtracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportseventtracker.Utils.calculateTimeLeft
import com.example.sportseventtracker.domain.GetSportsUseCase
import com.example.sportseventtracker.ui.mapper.toUiModel
import com.example.sportseventtracker.ui.model.MatchUiModel
import com.example.sportseventtracker.ui.model.SportUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _sports = MutableStateFlow<List<SportUiModel>>(emptyList())
    val sports: StateFlow<List<SportUiModel>> = _sports

    init {
        loadSportsData()
    }

    private fun loadSportsData() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val sports = getSportsUseCase.execute()

                _sports.value = sports.map { it.toUiModel() }.filter { it.matches.isNotEmpty() }
                startCountdownUpdater()

            } catch (e: Exception) {
                _sports.value = emptyList() // Clear the list or update with error state
            }
        }
    }

    private fun startCountdownUpdater() {
        viewModelScope.launch {
            while (true) {
                if (_sports.value.isNotEmpty()) {
                    _sports.value = _sports.value.map { sport ->
                        sport.copy(
                            matches = sport.matches.map { match ->
                                match.copy(
                                    timeLeft = calculateTimeLeft(match.matchStartTime)
                                )
                            }
                        )
                    }
                }
                delay(1000) // Update every second
            }
        }
    }

    fun setMatchFavourite(match: MatchUiModel) {
        _sports.value = _sports.value.map { sport ->
            sport.copy(
                matches = sport.matches.map { currentMatch ->
                    if (currentMatch.matchId == match.matchId) {
                        currentMatch.copy(isFavourite = match.isFavourite)
                    } else {
                        currentMatch
                    }
                }
            )
        }
    }

    fun filterSports(sportUiModel: SportUiModel) {
        _sports.value = _sports.value.map { sport ->
            if (sportUiModel.sportId == sport.sportId) {
                sport.copy(showFavoritesOnly = sportUiModel.showFavoritesOnly)
            } else {
                sport
            }
        }
    }
}
