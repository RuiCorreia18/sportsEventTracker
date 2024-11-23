package com.example.sportseventtracker.Utils

import java.util.concurrent.TimeUnit

fun calculateTimeLeft(matchStartTime: Long): String {
    val timeLeftMillis = matchStartTime - System.currentTimeMillis()
    return if (timeLeftMillis > 0) {
        formatMillisToHHMMSS(timeLeftMillis)
    } else {
        "00:00:00"
    }
}

fun formatMillisToHHMMSS(millis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
