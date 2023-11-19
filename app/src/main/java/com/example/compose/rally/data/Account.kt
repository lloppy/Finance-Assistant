package com.example.compose.rally.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Account(
    val name: String,
    val cardNumber: Int,
    val balance: Float,
    val color: Color  = listColors.get(1)// изменить тут
)

val listColors = listOf<Color>(
    Color(0xFF004940),
    Color(0xFF005D57),
    Color(0xFF04B97F),
    Color(0xFF37EFBA)
)

/**
 * Colors to add
 *             Color(0xFF004940)
 *            Color(0xFF005D57)
 *            Color(0xFF04B97F)
 *            Color(0xFF37EFBA)
 *
 * */