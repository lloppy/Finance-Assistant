package com.example.compose.rally.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.util.Date

@Immutable
data class Bill(
    val name: String,
    val date: LocalDateTime,
    val category: String,
    val amount: Float,
    val color: Color = (billCategoryColors[category] ?: billCategoryColors["Default"])!!
)

val billCategoryColors: Map<String, Color> = mapOf(
    "Квартира" to Color(0xFFFF6951),
    "ЖКХ" to Color(0xFFFCA496),
    "Продукты" to Color(0xFFD0FFD8),
    "Одежда и обувь" to Color(0xFFFFDC78),
    "Здоровье" to Color(0xFFFFAC12),
    "Интернет и телефон" to Color(0xFF673AB7),
    "Здоровье" to Color(0xFF1AD56D),
    "Другое" to Color(0xFFFFD7D0),
    "Default" to Color(0xFF4D4D4D)
    )

