package com.example.sportseventtracker.ui

data class SportUiModel(
    val sportId: String = "",
    val sportName: String,
    val matches: List<MatchUiModel>,
)

data class MatchUiModel(
    val matchId: String,
    val timeLeft: String = "",
    val matchStartTime: Long,
    val competitor1: String,
    val competitor2: String,
    val isFavourite: Boolean,
)
