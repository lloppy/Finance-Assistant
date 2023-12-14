package com.example.compose.rally.data.bill

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.compose.rally.data.util.localDateTimeToString
import java.time.LocalDateTime

@Immutable
data class Bill constructor(
    val name: String,
    val date: LocalDateTime,
    val timesRepeat: Int,
    val billPhoto: Uri?,
    val category: String,
    val mcc: Int?,
    val amount: Float,
    val color: Color = (billCategoryColors[category] ?: billCategoryColors["Default"])!!,
    val stringDate: String = localDateTimeToString(date)
)



val billCategoryColors: Map<String, Color> = mapOf(
    "Квартира" to Color(0xFF005853),
    "Дом и ремонт" to Color(0xFF005853),
    "Проезд" to Color(0xFF00413D),
    "Супермаркеты" to Color(0xFFFFFFFF),
    "Одежда и обувь" to Color(0xFF4CAF6D),
    "Мобильная связь" to Color(0xFF673AB7),
    "Здоровье" to Color(0xFF1AD56D),
    "QR" to Color(0xFF2196F3),
    "Фастфуд" to Color(0xFF4CAF6D),
    "Переводы" to Color(0xFF00BCD4),

    "Default" to Color(0xFF4D4D4D)
)
