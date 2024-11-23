package com.example.sportseventtracker.data

import com.example.sportseventtracker.domain.SportDomainModel
import com.example.sportseventtracker.domain.SportsRepository
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val api: SportsApi
) : SportsRepository {

    override suspend fun getSports(): List<SportDomainModel> {
        return api.getSports().mapNotNull { it.toDomainModel() }
    }
}
