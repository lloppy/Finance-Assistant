package com.example.compose.rally.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.compose.rally.data.util.localDateTimeToString
import com.example.compose.rally.data.util.localDateToString
import java.time.LocalDateTime

/**
 *  listOf("Только один день", "Каждый день", "Каждую неделю", "Каждый месяц")
 *  timesRepeat - это индекс массива
 *  так, если timesRepeat == 0, то значние - Только один день
 * */
@Immutable
data class Account(
    var name: String,
    val date: LocalDateTime,
    val dateRepeat: LocalDateTime,
    val timesRepeat: Int,
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