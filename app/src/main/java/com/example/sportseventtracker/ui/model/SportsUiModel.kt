package com.example.sportseventtracker.ui.model

data class SportUiModel(
    val sportId: String,
    val sportName: String,
    val matches: List<MatchUiModel>,
    val showFavoritesOnly: Boolean = false,
)

data class MatchUiModel(
    val matchId: String,
    val timeLeft: String = "",
    val matchStartTime: Long,
    val competitor1: String,
    val competitor2: String,
    val isFavourite: Boolean = false,
)
