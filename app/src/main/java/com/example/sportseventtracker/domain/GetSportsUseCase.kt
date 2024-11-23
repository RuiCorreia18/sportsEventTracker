package com.example.sportseventtracker.domain

import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    private val repository: SportsRepository
) {

    suspend fun execute(): List<SportDomainModel> {
        val currentTimeMillis = System.currentTimeMillis()
        val sports = repository.getSports()

        return sports.map { sport ->
            sport.copy(
                matchesList = sport.matchesList.filter { match ->
                    match.matchStartTime > currentTimeMillis
                }
            )
        }
    }
}
