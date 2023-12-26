package com.example.compose.rally.data.analyze

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.AccountRepository.Companion.getAllAccounts
import com.example.compose.rally.data.bill.BillRepository.Companion.bills
import java.time.DayOfWeek


@RequiresApi(Build.VERSION_CODES.O)
fun getBillMCCReport(): String {
    val mccExpenses = calculateBillMCCExpenses()

    return """
Ваш отчет по наибольшим тратам на основе MCC-кодов:
        
${formatTop5MCCExpenses(mccExpenses)}

Благодарим за использование нашего помощника!.""".trimIndent()
}

@RequiresApi(Build.VERSION_CODES.O)
private fun calculateBillMCCExpenses(): Map<Int, Double> {
    return bills.filter { it.mcc != null }
        .groupBy { it.mcc!! }
        .mapValues { (_, mccBills) ->
            mccBills.sumOf { it.amount.toDouble() }
        }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun findMostSpendingMCC(): List<Int> {
    val mccExpenses = bills.filter { it.mcc != null }
        .groupBy { it.mcc!! }
        .mapValues { (_, mccBills) ->
            mccBills.sumOf { it.amount.toDouble() }
        }

    return mccExpenses
        .toList()
        .sortedByDescending { it.second }
        .take(5)
        .map { it.first }
}

private fun formatTop5MCCExpenses(mccExpenses: Map<Int, Double>): String {
    val top5Expenses = mccExpenses
        .toList()
        .sortedByDescending { it.second }
        .take(5)

    val formattedExpenses = StringBuilder()

    for ((mcc, expense) in top5Expenses) {
        val mccString = mcc.toString()
        formattedExpenses.append("MCC $mccString: всего потречено ${expense.toInt()} руб\n")
    }

    return formattedExpenses.toString()
}


fun getAccountStatisticReport(): String {
    val totalSum = calculateAccountTotalSum()
    val categoryExpenses = calculateAccountCategoryExpenses()
    val mostSpendingDay = findAccountMostSpendingDay()

    return """
ПОСТУПЛЕНИЯ:

Всего: ${totalSum.toInt()} руб

Поступления по категориям:
    
${formatCategoryExpenses(categoryExpenses)}
Больше всего средств посупило в ${mostSpendingDay ?: "неизвестно"}""".trimIndent()
}


private fun formatCategoryExpenses(categoryExpenses: Map<String, Float>): String {
    val formattedExpenses = StringBuilder()

    for ((category, expense) in categoryExpenses) {
        formattedExpenses.append("$category: ${expense.toInt()} руб\n")
    }

    return formattedExpenses.toString()
}

fun calculateAccountTotalSum(): Float {
    return getAllAccounts().sumOf { it.balance.toDouble() }.toFloat()
}

fun calculateAccountCategoryExpenses(): Map<String, Float> {
    return getAllAccounts().drop(1).groupBy { it.category }
        .mapValues { (_, categoryAccounts) ->
            categoryAccounts.sumOf { it.balance.toDouble() }.toFloat()
        }
}

fun findAccountMostSpendingDay(): DayOfWeek? {
    val dayExpenses = getAllAccounts().groupBy { it.date.dayOfWeek }
        .mapValues { (_, dayAccounts) ->
            dayAccounts.sumOf { it.balance.toDouble() }.toFloat()
        }

    return dayExpenses.maxByOrNull { it.value }?.key
}

