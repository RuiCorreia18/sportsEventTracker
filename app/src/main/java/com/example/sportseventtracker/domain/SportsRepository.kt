package com.example.sportseventtracker.domain

interface SportsRepository {
    suspend fun getSports(): List<SportDomainModel>
    suspend fun updateFavouriteInDb(sportId: String, matchId: String, isFavourite: Boolean)
}
