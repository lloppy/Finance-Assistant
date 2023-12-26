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
    "Квартира" to Color(0xFFAD06A9),
    "Дом и ремонт" to Color(0xFFA509DD),
    "Проезд" to Color(0xFF673AB7),
    "Супермаркеты" to Color(0xFFDA91D8),
    "Одежда и обувь" to Color(0xFFB34CB0),
    "Мобильная связь" to Color(0xFFB208F0),
    "Здоровье" to Color(0xFFF30FED),
    "QR" to Color(0xFFDA91D8),
    "Фастфуд" to Color(0xFFF03AEB),
    "Переводы" to Color(0xFFD3ABF7),

    "Default" to Color(0xFF4D4D4D)
)
