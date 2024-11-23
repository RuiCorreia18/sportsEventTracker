package com.example.sportseventtracker.domain

import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    private val repository: SportsRepository
) {

    suspend operator fun invoke(): List<SportDomainModel> {
        val currentTimeMillis = System.currentTimeMillis()
        val sports = repository.getSports()

        return sports.filter { it.matchesList.isNotEmpty() }.map { sport ->
            sport.copy(
                matchesList = sport.matchesList.filter { match ->
                    match.matchStartTime > currentTimeMillis
                }
            )
        }
    }
}
