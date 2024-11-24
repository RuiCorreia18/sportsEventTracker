package com.example.sportseventtracker.utils

import android.content.Context
import com.example.sportseventtracker.R

class StringProvider(private val context: Context) {

    fun getString(stringResource: Int): String {
        return context.getString(stringResource)
    }

    fun getFormattedTime(hours: Long, minutes: Long, seconds: Long): String {
        return context.getString(R.string.time_format, hours, minutes, seconds)
    }
}
