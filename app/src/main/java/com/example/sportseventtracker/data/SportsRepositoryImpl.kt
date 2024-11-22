package com.example.sportseventtracker.data

import com.example.sportseventtracker.domain.SportsRepository

class SportsRepositoryImpl(
    private val api: SportsApi
): SportsRepository{

    override suspend fun getSports() {
        TODO("Not yet implemented")
    }
}
