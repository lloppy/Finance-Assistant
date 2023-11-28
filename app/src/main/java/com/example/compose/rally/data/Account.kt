package com.example.compose.rally.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Account(
    var name: String,
    val cardNumber: Int,
    val balance: Float,
    var category: String,
    val color: Color = accountCategoryColors[category]!!
    )

val accountCategoryColors: Map<String, Color> = mapOf(
    "Зарплата" to Color(0xFF004940),
    "Стипендия" to Color(0xFF005D57),
    "Инвестиции" to Color(0xFF04B97F),
    "Подработка" to Color(0xFF37EFBA)
)