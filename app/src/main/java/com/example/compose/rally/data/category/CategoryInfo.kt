package com.example.compose.rally.data.category

import androidx.compose.ui.graphics.Color

data class CategoryInfo(
    val name: String,
    val color: Color
)


val accountCategories: List<CategoryInfo> = listOf(
    CategoryInfo("Зарплата", Color(0xFF004940)),
    CategoryInfo("Стипендия", Color(0xFF005D57)),
    CategoryInfo("Инвестиции", Color(0xFF04B97F)),
    CategoryInfo("Подработка", Color(0xFF37EFBA)),

    CategoryInfo("Default", Color(0xFF4D4D4D))
    )

val billCategories: List<CategoryInfo> = listOf(
    CategoryInfo("Квартира", Color(0xFFFF6951)),
    CategoryInfo("ЖКХ", Color(0xFFFCA496)),
    CategoryInfo("Продукты", Color(0xFFD0FFD8)),
    CategoryInfo("Одежда и обувь", Color(0xFFFFDC78)),
    CategoryInfo("Здоровье", Color(0xFFFFAC12)),
    CategoryInfo("Интернет и телефон", Color(0xFF673AB7)),
    CategoryInfo("Здоровье", Color(0xFF1AD56D)),
    CategoryInfo("QR", Color(0xFF2196F3)),
    CategoryInfo("Другое", Color(0xFFFFD7D0)),

    CategoryInfo("Default", Color(0xFF4D4D4D))
)


val getAccountCategories: List<CategoryInfo>
    get() = accountCategories


val getBillCategories: List<CategoryInfo>
    get() = billCategories


