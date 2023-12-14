package com.example.compose.rally.data.cashback


data class CreditCard(
    val name: String,
    val cashbackCategories: List<CashbackCategory>
)