package com.example.sportseventtracker.domain

interface SportsRepository {
    suspend fun getSports(): List<SportDomainModel>
}
