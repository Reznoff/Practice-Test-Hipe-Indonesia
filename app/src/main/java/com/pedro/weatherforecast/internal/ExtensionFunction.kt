package com.pedro.weatherforecast.internal

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun String.epochToData(): String {
    return Date(this.toLong().times(1000)).toString()
}

@SuppressLint("SimpleDateFormat")
fun Long.epochToDate(): String {
    val date = Date(this.times(1000))
    return SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(date)
}

@SuppressLint("SimpleDateFormat")
fun Long.epochToDay(): String {
    val date = Date(this.times(1000))
    return SimpleDateFormat("EEE, MMM dd").format(date)
}

@SuppressLint("SimpleDateFormat")
fun Long.epochToHour(): String {
    val date = Date(this.times(1000))
    return SimpleDateFormat("HH:mm").run {
        timeZone = TimeZone.getTimeZone("GMT+7")
        format(date)
    }
}

fun Double.toPercentage(): String {
    return "${this.times(100).toInt()}%"
}