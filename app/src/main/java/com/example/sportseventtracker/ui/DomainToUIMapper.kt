package com.example.sportseventtracker.ui

import com.example.sportseventtracker.domain.MatchDomainModel
import com.example.sportseventtracker.domain.SportDomainModel


fun SportDomainModel.toUiModel() =
    SportUiModel(
        sportName = this.sportName,
        matches = this.matchesList.map { it.toUiModel() }

    )

fun MatchDomainModel.toUiModel(): MatchUiModel {
    val competitors = this.matchName.split("-")

    return MatchUiModel(
        matchId = this.matchId,
        timeLeft = "",
        matchStartTime = this.matchStartTime,
        competitor1 = competitors[0].trim(),
        competitor2 = competitors[1].trim(),
        isFavourite = this.isFavourite
    )

}
