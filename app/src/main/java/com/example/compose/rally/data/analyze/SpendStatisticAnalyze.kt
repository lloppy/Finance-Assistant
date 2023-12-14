package com.example.compose.rally.data.analyze

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.bill.BillRepository.Companion.bills
import java.time.DayOfWeek


@RequiresApi(Build.VERSION_CODES.O)
fun getBillSpendStatisticReport(): String {
    val totalSum = calculateBillTotalSum()
    val categoryExpenses = calculateBillCategoryExpenses()
    val mostSpendingDay = findBillMostSpendingDay()

    val report = """
    Ваша финансовая статистика за последнее время выглядит следующим образом:

    Общая сумма расходов: $totalSum


    Расходы по категориям:
    
${formatCategoryExpenses(categoryExpenses)}
    Самый "дорогой" день недели: ${mostSpendingDay ?: "нет данных"}""".trimIndent()

    return report
}


private fun formatCategoryExpenses(categoryExpenses: Map<String, Float>): String {
    val formattedExpenses = StringBuilder()

    for ((category, expense) in categoryExpenses) {
        formattedExpenses.append("$category: $expense\n")
    }

    return formattedExpenses.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateBillTotalSum(): Float {
    return bills.sumOf { it.amount.toDouble() }.toFloat()
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateBillCategoryExpenses(): Map<String, Float> {
    return bills.groupBy { it.category }
        .mapValues { (_, categoryBills) ->
            categoryBills.sumOf { it.amount.toDouble() }.toFloat()
        }
}

@RequiresApi(Build.VERSION_CODES.O)
fun findBillMostSpendingDay(): DayOfWeek? {
    val dayExpenses = bills.groupBy { it.date.dayOfWeek }
        .mapValues { (_, dayBills) ->
            dayBills.sumOf { it.amount.toDouble() }.toFloat()
        }

    return dayExpenses.maxByOrNull { it.value }?.key
}

