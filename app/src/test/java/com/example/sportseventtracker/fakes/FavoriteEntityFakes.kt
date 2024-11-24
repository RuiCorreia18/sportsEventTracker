package com.example.sportseventtracker.fakes

import com.example.sportseventtracker.data.local.FavoriteEntity

object FavoriteEntityFakes {

    val mockFavoriteEntity1 = FavoriteEntity(
        sportId = "sport_1",
        matchId = "match_1_2",
        isFavourite = false
    )
    val mockFavoriteEntity2 = FavoriteEntity(
        sportId = "sport_1",
        matchId = "match_1_1",
        isFavourite = true
    )
}
