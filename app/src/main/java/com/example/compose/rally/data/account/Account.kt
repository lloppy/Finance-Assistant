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
    var date: LocalDateTime,
    val timesRepeat: Int,
    val cardNumber: Int,
    val balance: Float,
    var category: String,
    val color: Color = (accountCategoryColors[category] ?: accountCategoryColors["Default"])!!,
    val stringDate: String = localDateToString(date)
)

val accountCategoryColors: Map<String, Color> = mapOf(
    "Зарплата" to Color(0xD5E167F7),
    "Перевод средств от организации" to Color(0xFFEBAEE9),
    "Стипендия" to Color(0xFFC027EB),
    "Инвестиции" to Color(0xC8FC9EE7),
    "Перевод между счетами" to Color(0xFF8A0CE2),
    "Переводы" to Color(0xFFDA91D8),
    "Бонусы" to Color(0xFFB96EEC),

    "Default" to Color(0xFF4D4D4D)
)