package com.example.sportseventtracker.fakes

import com.example.sportseventtracker.data.MatchResponse
import com.example.sportseventtracker.data.SportResponse
import com.google.gson.JsonPrimitive

object SportResponseFakes {
    val mockSportResponse1 = SportResponse(
        sportId = "sport_1",
        sportName = JsonPrimitive("Soccer"),
        matchList = listOf(
            MatchResponse(
                matchId = "match_1_2",
                sportId = "sport_1",
                matchName = "Team C - Team D",
                matchStartTime = 1921769591
            ),
            MatchResponse(
                matchId = "match_1_1",
                sportId = "sport_1",
                matchName = "Team A - Team B",
                matchStartTime = 1921769590
            )
        )
    )

    val mockSportResponse2 = SportResponse(
        sportId = "sport_2",
        sportName = JsonPrimitive("Basketball"),
        matchList = listOf(
            MatchResponse(
                matchId = "match_2_2",
                sportId = "sport_2",
                matchName = "Team Z - Team W",
                matchStartTime = 1290617591
            )
        )
    )

    val mockSportResponse3 = SportResponse(
        sportId = "sport_3",
        sportName = JsonPrimitive("Tennis"),
        matchList = listOf(
            MatchResponse(
                matchId = "match_3_1",
                sportId = "sport_3",
                matchName = "Player 1 - Player 2",
                matchStartTime = 1921769591
            ),
            MatchResponse(
                matchId = "match_3_2",
                sportId = "sport_3",
                matchName = "Player 3 - Player 4",
                matchStartTime = 1290617591
            )
        )
    )
}
