package com.example.sportseventtracker.data

import com.example.sportseventtracker.domain.MatchDomainModel
import com.example.sportseventtracker.domain.SportDomainModel
import com.example.sportseventtracker.ui.SECOND_IN_MILLIS

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
        matchStartTime = fromUnixToMillis(this.matchStartTime)
    )


private fun fromUnixToMillis(unix: Int) = unix * SECOND_IN_MILLIS
