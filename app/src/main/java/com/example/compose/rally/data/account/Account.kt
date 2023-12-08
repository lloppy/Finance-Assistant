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
    "Зарплата" to Color(0xFFF44336),
    "Стипендия" to Color(0xFFFF5722),
    "Инвестиции" to Color(0xFF4CAF50),
    "Переводы" to Color(0xFF001CB8),
    "Бонусы" to Color(0xFFFFEB3B),

    "Default" to Color(0xFF4D4D4D)
)