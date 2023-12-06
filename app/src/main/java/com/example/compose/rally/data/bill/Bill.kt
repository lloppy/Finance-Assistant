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
    val amount: Float,
    val color: Color = (billCategoryColors[category] ?: billCategoryColors["Default"])!!,
    val stringDate: String = localDateTimeToString(date)
)


val billCategoryColors: Map<String, Color> = mapOf(
    "Квартира" to Color(0xFFFF6951),
    "ЖКХ" to Color(0xFFFCA496),
    "Продукты" to Color(0xFFD0FFD8),
    "Одежда и обувь" to Color(0xFFFFDC78),
    "Здоровье" to Color(0xFFFFAC12),
    "Интернет и телефон" to Color(0xFF673AB7),
    "Здоровье" to Color(0xFF1AD56D),
    "QR" to Color(0xFF2196F3),
    "Другое" to Color(0xFFFFD7D0),
    "Default" to Color(0xFF4D4D4D)
)
