package com.example.compose.rally.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.compose.rally.data.util.localDateTimeToString
import com.example.compose.rally.data.util.localDateToString
import java.time.LocalDateTime

@Immutable
data class Account(
    var name: String,
    val date: LocalDateTime,
    val cardNumber: Int,
    val balance: Float,
    var category: String,
    val color: Color = (accountCategoryColors[category] ?: accountCategoryColors["Default"])!!,
    val stringDate: String = localDateToString(date)
)

val accountCategoryColors: Map<String, Color> = mapOf(
    "Зарплата" to Color(0xFF004940),
    "Стипендия" to Color(0xFF005D57),
    "Инвестиции" to Color(0xFF04B97F),
    "Default" to Color(0xFF4D4D4D),
    "Подработка" to Color(0xFF37EFBA)
)