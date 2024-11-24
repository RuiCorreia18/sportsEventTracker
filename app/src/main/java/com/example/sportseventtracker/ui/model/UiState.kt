package com.example.sportseventtracker.ui.model

sealed interface UiState {
    object Empty : UiState
    object Loading : UiState
    data class Success(val sports: List<SportUiModel>) : UiState
    data class Error(val message: String, val exception: Throwable, val tag: String) : UiState
    data class InternalError(val message: String, val exception: Throwable, val tag: String) : UiState
}
