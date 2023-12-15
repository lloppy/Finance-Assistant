package com.example.compose.rally.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val Green500 = Color(0xFF1EB980)
val DarkBlue900 = Color(0xFF26282F)
val Ender = Color(0xFFDAA6D9)
val Purple = Color(0xFF9F85CE)

// Rally is always dark themed.
val ColorPalette = darkColors(
    primary = Ender,
    surface = DarkBlue900,
    onSurface = Color.White,
    background = DarkBlue900,
    onBackground = Color.White
)
