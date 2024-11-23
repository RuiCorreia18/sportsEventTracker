package com.example.sportseventtracker.utils

import java.util.concurrent.TimeUnit

private const val TO_MINUTES_AND_SECONDS = 60

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
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % TO_MINUTES_AND_SECONDS
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % TO_MINUTES_AND_SECONDS

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
