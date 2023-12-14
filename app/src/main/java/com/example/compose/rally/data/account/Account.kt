package com.example.compose.rally.data.account

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
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
    val timesRepeat: Int,
    val cardNumber: Int,
    val balance: Float,
    var category: String,
    val color: Color = (accountCategoryColors[category] ?: accountCategoryColors["Default"])!!,
    val stringDate: String = localDateToString(date)
)

val accountCategoryColors: Map<String, Color> = mapOf(
    "Зарплата" to Color(0xFF005853),
    "Перевод средств от организации" to Color(0xFF4CAF6D),
    "Стипендия" to Color(0xFF4CAF6D),
    "Инвестиции" to Color(0xFF00413D),
    "Перевод между счетами" to Color(0xFF27FDC0),
    "Переводы" to Color(0xFF00BCD4),
    "Бонусы" to Color(0xFF3F51B5),

    "Default" to Color(0xFF4D4D4D)
)