package com.example.compose.rally.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Bill(
    val name: String,
    val due: String,
    val amount: Float,
    val color: Color
)