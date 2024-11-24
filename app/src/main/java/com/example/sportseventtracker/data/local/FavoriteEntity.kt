package com.example.sportseventtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sportId: String,
    val matchId: String,
    val isFavourite: Boolean,
)
