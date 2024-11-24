package com.example.sportseventtracker.utils

import java.util.concurrent.TimeUnit

private const val TIME_UNIT_DIVISOR = 60
fun calculateTimeLeft(matchStartTime: Long): Long {
    return matchStartTime - System.currentTimeMillis()
}

fun formatTimeLeft(timeLeftMillis: Long, stringProvider: StringProvider): String {
    val hours = TimeUnit.MILLISECONDS.toHours(timeLeftMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftMillis) % TIME_UNIT_DIVISOR
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftMillis) % TIME_UNIT_DIVISOR

    return stringProvider.getFormattedTime(hours, minutes, seconds)
}
