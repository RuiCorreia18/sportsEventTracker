package com.example.sportseventtracker.data

import com.example.sportseventtracker.data.local.FavoriteDao
import com.example.sportseventtracker.data.local.FavoriteEntity
import com.example.sportseventtracker.domain.SportDomainModel
import com.example.sportseventtracker.domain.SportsRepository
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val api: SportsApi,
    private val favoriteDao: FavoriteDao,
) : SportsRepository {

    override suspend fun getSports(): List<SportDomainModel> {

        val sports = api.getSports().mapNotNull { it.toDomainModel() }
        val favorites = favoriteDao.getAll().associateBy { Pair(it.sportId, it.matchId) }

        return sports.map { sport ->
            sport.copy(
                matchesList = sport.matchesList.map { match ->
                    val isFavourite = favorites[Pair(sport.sportId, match.matchId)]?.isFavourite ?: false
                    match.copy(isFavourite = isFavourite)
                }
            )
        }
    }

    override suspend fun updateFavouriteInDb(
        sportId: String,
        matchId: String,
        isFavourite: Boolean
    ) {
        val favouriteEntity = FavoriteEntity(
            sportId = sportId,
            matchId = matchId,
            isFavourite = isFavourite
        )
        favoriteDao.insert(favouriteEntity)
    }
}
