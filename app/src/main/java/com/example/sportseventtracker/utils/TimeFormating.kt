package com.example.sportseventtracker.utils

import com.example.sportseventtracker.R
import java.util.concurrent.TimeUnit

private const val TIME_UNIT_DIVISOR = 60

fun calculateTimeLeft(matchStartTime: Long, stringProvider: StringProvider): String {
    val timeLeftMillis = matchStartTime - System.currentTimeMillis()

    return if (timeLeftMillis > 0) {
        val hours = TimeUnit.MILLISECONDS.toHours(timeLeftMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftMillis) % TIME_UNIT_DIVISOR
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftMillis) % TIME_UNIT_DIVISOR

        stringProvider.getFormattedTime(hours, minutes, seconds)
    } else {
        stringProvider.getString(R.string.countdown_end_time)
    }

}
