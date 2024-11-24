package com.example.sportseventtracker.fakes

import com.example.sportseventtracker.domain.MatchDomainModel
import com.example.sportseventtracker.domain.SportDomainModel
import com.example.sportseventtracker.ui.model.MatchUiModel
import com.example.sportseventtracker.ui.model.SportUiModel

object SportsFakes {

    val mockSportDomainModel1 = SportDomainModel(
        sportId = "sport_1",
        sportName = "Soccer",
        matchesList = listOf(
            MatchDomainModel(
                matchId = "match_1_2",
                matchName = "Team C - Team D",
                matchStartTime = 1921769591000, // in future
                isFavourite = false
            ),
            MatchDomainModel(
                matchId = "match_1_1",
                matchName = "Team A - Team B",
                matchStartTime = 1921769590000, // future - 1
                isFavourite = true
            ),
        )
    )

    val mockSportUiModel1 = SportUiModel(
        sportId = "sport_1",
        sportName = "Soccer",
        showFavoritesOnly = false,
        matches = listOf(
            MatchUiModel(
                matchId = "match_1_2",
                timeLeft = "",
                matchStartTime = 1921769591000,
                competitor1 = "Team C",
                competitor2 = "Team D",
                isFavourite = false
            ),
            MatchUiModel(
                matchId = "match_1_1",
                timeLeft = "",
                matchStartTime = 1921769590000,
                competitor1 = "Team A",
                competitor2 = "Team B",
                isFavourite = true
            ),
        )

    )

    val mockSportDomainModel2 = SportDomainModel(
        sportId = "sport_2",
        sportName = "Basketball",
        matchesList = listOf(
            MatchDomainModel(
                matchId = "match_2_2",
                matchName = "Team Z - Team W",
                matchStartTime = 1290617591000, // in past
                isFavourite = false
            )
        )
    )

    val mockSportDomainModel3 = SportDomainModel(
        sportId = "sport_3",
        sportName = "Tennis",
        matchesList = listOf(
            MatchDomainModel(
                matchId = "match_3_1",
                matchName = "Player 1 - Player 2",
                matchStartTime = 1921769591000, // in future
                isFavourite = false
            ),
            MatchDomainModel(
                matchId = "match_3_2",
                matchName = "Player 3 - Player 4",
                matchStartTime = 1290617591000, // in past
                isFavourite = false
            )
        )
    )

    val mockExpectedAfterUseCase = listOf(
        SportDomainModel(
            sportId = "sport_1",
            sportName = "Soccer",
            matchesList = listOf(
                MatchDomainModel(
                    matchId = "match_1_1",
                    matchName = "Team A - Team B",
                    matchStartTime = 1921769590000, // in future
                    isFavourite = true
                ),
                MatchDomainModel(
                    matchId = "match_1_2",
                    matchName = "Team C - Team D",
                    matchStartTime = 1921769591000, // in future
                    isFavourite = false
                ),
            )
        ),
        SportDomainModel(
            sportId = "sport_2",
            sportName = "Basketball",
            matchesList = emptyList()
        ),
        SportDomainModel(
            sportId = "sport_3",
            sportName = "Tennis",
            matchesList = listOf(
                MatchDomainModel(
                    matchId = "match_3_1",
                    matchName = "Player 1 - Player 2",
                    matchStartTime = 1921769591000, // in future
                    isFavourite = false
                ),
            )
        )
    )
}
