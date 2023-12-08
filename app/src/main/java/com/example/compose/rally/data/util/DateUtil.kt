package com.example.compose.rally.data.util

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun localDateTimeToString(localDateTime: LocalDateTime): String {
    val pattern: String = if (localDateTime.hour == 0 && localDateTime.minute == 0) {
        "dd.MM.yyyy"
    } else {
        "dd.MM.yyyy HH:mm"
    }

    val formatter = DateTimeFormatter.ofPattern(pattern)

    val formattedDate = localDateTime.format(formatter)
    Log.e("BillDate", formattedDate)

    return formattedDate
}

fun localDateToString(localDateTime: LocalDateTime): String {
    val pattern = "dd.MM.yyyy"
    val formatter = DateTimeFormatter.ofPattern(pattern)

    val formattedDate = localDateTime.format(formatter)
    Log.e("AccountDate", formattedDate)

    return formattedDate
}