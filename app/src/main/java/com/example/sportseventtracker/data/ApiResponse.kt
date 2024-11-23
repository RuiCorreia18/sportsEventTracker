package com.example.sportseventtracker.data

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class SportResponse(
    @SerializedName("i")
    val sportId: String,
    @SerializedName("d")
    val sportName: JsonElement,
    @SerializedName("e")
    val matchList: List<MatchResponse>
)

data class MatchResponse(
    @SerializedName("i")
    val matchId: String,
    @SerializedName("si")
    val sportId: String,
    @SerializedName("d")
    val matchName: String,
    @SerializedName("tt")
    val matchStartTime: Int
)
