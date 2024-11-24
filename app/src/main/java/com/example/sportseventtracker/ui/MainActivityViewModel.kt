package com.example.sportseventtracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportseventtracker.R
import com.example.sportseventtracker.domain.GetSportsUseCase
import com.example.sportseventtracker.ui.mapper.toUiModel
import com.example.sportseventtracker.ui.model.MatchUiModel
import com.example.sportseventtracker.ui.model.SportUiModel
import com.example.sportseventtracker.ui.model.UiState
import com.example.sportseventtracker.utils.StringProvider
import com.example.sportseventtracker.utils.calculateTimeLeft
import com.example.sportseventtracker.utils.formatTimeLeft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SECOND_IN_MILLIS = 1000L

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val stringProvider: StringProvider,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadSportsData()
    }

    private fun loadSportsData() {
        viewModelScope.launch {
            try {
                val sports = getSportsUseCase().map { it.toUiModel() }

                if (sports.isNotEmpty()) {
                    _uiState.value = UiState.Success(sports)
                    startCountdownUpdater()
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    message = stringProvider.getString(R.string.request_error),
                    exception = e,
                    tag = "loadSportsData"
                )
            }
        }
    }

    private fun startCountdownUpdater() {
        viewModelScope.launch {
            while (true) {
                if (uiState.value is UiState.Success) {
                    val sports = (uiState.value as UiState.Success).sports
                    val updatedSports = sports.map { sport ->
                        sport.copy(
                            matches = sport.matches.map { match ->
                                val timeLeftMillis = calculateTimeLeft(match.matchStartTime)
                                if (timeLeftMillis >= 0) {
                                    match.copy(
                                        timeLeft = formatTimeLeft(
                                            timeLeftMillis,
                                            stringProvider
                                        )
                                    )
                                } else {
                                    match
                                }
                            }
                        )
                    }
                    _uiState.value = UiState.Success(updatedSports)
                    delay(SECOND_IN_MILLIS)
                }
            }
        }
    }

    fun setMatchFavourite(match: MatchUiModel) {
        val sports = (uiState.value as UiState.Success).sports
        val updatedSports = sports.map { sport ->
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
        _uiState.value = UiState.Success(updatedSports)
    }

    fun filterSports(sportUiModel: SportUiModel) {
        val sports = (uiState.value as UiState.Success).sports
        val updatedSports = sports.map { sport ->
            if (sportUiModel.sportId == sport.sportId) {
                sport.copy(showFavoritesOnly = sportUiModel.showFavoritesOnly)
            } else {
                sport
            }
        }
        _uiState.value = UiState.Success(updatedSports)
    }
}
