package com.example.compose.rally.data.cashback

data class CashbackCategory(
    val categoryName: String,
    val mccCodes: List<Int>,
    val cashbackPercentage: Double
)


fun findSuitableCard(mccCategories: List<Int>): CreditCard? {
    val availableCards = listOf(
        CreditCard(
            name = "Нет кэшбэка",
            cashbackCategories = emptyList()
        ),
        CreditCard(
            name = "Альфа-Карта / 365 дней без %",
            cashbackCategories = listOf(
                CashbackCategory("Авто", listOf(4784, 5013, 5271, 5511, 5521, 5531, 5532, 5533, 5551, 5561, 5571, 5592, 5598, 5599, 7511, 7523, 7531, 7534, 7535, 7538, 7542, 7549), 0.05),
                CashbackCategory("АЗС", listOf(3990, 5172, 5541, 5542, 5552, 5983, 9752), 0.05),
                CashbackCategory("Аренда авто", listOf(3400, 3410, 3412, 3423, 3425, 3439, 3441, 3990, 7512, 7513, 7519 ) + (3351..3398).toList(), 0.05),
                CashbackCategory("Дом и ремонт", listOf(780, 1520, 1711, 1731, 1740, 1750, 1761, 1771, 2842, 3990, 5021, 5039, 5046, 5051, 5072, 5074, 5085, 5198, 5200, 5211, 5231, 5251, 5261, 5718, 5719, 5950, 5996, 7217, 7641, 7692, 7699) + (5712..5714).toList(), 0.05),
                CashbackCategory("Животные", listOf(742, 5995), 0.05),
                CashbackCategory("Здоровье", listOf(4119, 5047, 5122, 5912, 5975, 5976, 8011, 8021, 8031, 8049, 8050, 8062, 8071, 8099) + (8041..8044).toList(), 0.05),
                CashbackCategory("Кафе и рестораны", listOf(3990) + (5811..5813).toList(), 0.05),
                CashbackCategory("Книги", listOf(2741, 5111, 5192, 5942, 5943, 5994), 0.05),
                CashbackCategory("Красота", listOf(5977, 7230, 7297, 7298), 0.05),
                CashbackCategory("Маркетплейсы", listOf(3990, 3991, 5262, 5300, 5399, 5964), 0.05),
                CashbackCategory("Красота", listOf(5977, 7230, 7297, 7298), 0.05),
                CashbackCategory("Образование", listOf(3990, 8211, 8220, 8241, 8244, 8249, 8299, 8351), 0.05),
                CashbackCategory("Одежда и обувь", listOf(5137, 5139, 5611, 5621, 5631, 5641, 5651, 5661, 5681, 5691, 5931, 5948, 7251, 7296, 7631) + (5697..5699).toList(), 0.05),
                CashbackCategory("Продукты", listOf(3990, 3991, 5310, 5311, 5331, 5411, 5422, 5441, 5451, 5462, 5499, 7278, 9751), 0.05),
                CashbackCategory("Путешествия", (3000..3302).toList() + (3500..3838).toList() + listOf( 4411, 4511, 4722, 4723, 5962, 7011, 7033), 0.05),

                )
        )
    )

    return availableCards
        .filter { card ->
            card.cashbackCategories.any { cashbackCategory ->
                mccCategories.any { it in cashbackCategory.mccCodes }
            }
        }
        .minByOrNull { it.cashbackCategories.sumOf { category -> category.cashbackPercentage } }
}