package com.example.sportseventtracker.data

import com.example.sportseventtracker.domain.MatchDomainModel
import com.example.sportseventtracker.domain.SportDomainModel

fun SportResponse.toDomainModel(): SportDomainModel? {
    return if (!this.sportName.isJsonArray) {
        SportDomainModel(
            sportId = this.sportId,
            sportName = this.sportName.asString,
            matchesList = this.matchList.map { it.toDomainModel() }
        )
    } else {
        null
    }
}


fun MatchResponse.toDomainModel() =
    MatchDomainModel(
        matchId = this.matchId,
        matchName = this.matchName,
        matchStartTime = this.matchStartTime * 1000L //Convert Unix to Milliseconds
    )
