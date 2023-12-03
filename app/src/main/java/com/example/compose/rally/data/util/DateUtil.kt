package com.example.compose.rally.data.util

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtil {

}


fun localDateTimeToString(localDateTime: LocalDateTime): String {
    val pattern = "dd.MM.yyyy HH:mm"
    val formatter = DateTimeFormatter.ofPattern(pattern)

    val formattedDate = localDateTime.format(formatter)
    Log.e("BillDate", formattedDate)

    return formattedDate
}