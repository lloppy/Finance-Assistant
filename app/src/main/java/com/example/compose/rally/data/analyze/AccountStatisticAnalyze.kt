package com.example.compose.rally.data.analyze

import com.example.compose.rally.data.account.AccountRepository.Companion.accounts
import java.time.DayOfWeek


fun getAccountStatisticReport(): String {
    val totalSum = calculateAccountTotalSum()
    val categoryExpenses = calculateAccountCategoryExpenses()
    val mostSpendingDay = findAccountMostSpendingDay()

    val report = """
    Ваша финансовая статистика за последнее время выглядит следующим образом:

    Общая сумма поступлений: $totalSum


    Поступления по категориям:
    
${formatCategoryExpenses(categoryExpenses)}
    Самый прибыльный день недели: ${mostSpendingDay ?: "нет данных"}

    Благодарим за использование нашего помощника!.""".trimIndent()

    return report
}


private fun formatCategoryExpenses(categoryExpenses: Map<String, Float>): String {
    val formattedExpenses = StringBuilder()

    for ((category, expense) in categoryExpenses) {
        formattedExpenses.append("$category: $expense\n")
    }

    return formattedExpenses.toString()
}

fun calculateAccountTotalSum(): Float {
    return accounts.sumOf { it.balance.toDouble() }.toFloat()
}

fun calculateAccountCategoryExpenses(): Map<String, Float> {
    return accounts.groupBy { it.category }
        .mapValues { (_, categoryAccounts) ->
            categoryAccounts.sumOf { it.balance.toDouble() }.toFloat()
        }
}

fun findAccountMostSpendingDay(): DayOfWeek? {
    val dayExpenses = accounts.groupBy { it.date.dayOfWeek }
        .mapValues { (_, dayAccounts) ->
            dayAccounts.sumOf { it.balance.toDouble() }.toFloat()
        }

    return dayExpenses.maxByOrNull { it.value }?.key
}

