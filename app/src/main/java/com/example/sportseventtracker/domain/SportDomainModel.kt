package com.example.sportseventtracker.domain

data class SportDomainModel(
    val sportId: String,
    val sportName: String,
    val matchesList: List<MatchDomainModel>
)

data class MatchDomainModel(
    val matchId: String,
    val matchName: String,
    val matchStartTime: Long,
    val isFavourite: Boolean = false,
)
