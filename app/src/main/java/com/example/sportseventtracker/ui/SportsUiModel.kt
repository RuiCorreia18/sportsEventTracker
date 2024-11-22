package com.example.sportseventtracker.ui

data class SportsUiModel(
    val sportId: String,
    val sportName: String,
    val isFavourite: Boolean,
    val matches: List<MatchUiModel>,
)

data class MatchUiModel(
    val matchId: String,
    val timeLeft: String,
    val competitor1: String,
    val competitor2: String,
    val isFavourite: Boolean,
)
