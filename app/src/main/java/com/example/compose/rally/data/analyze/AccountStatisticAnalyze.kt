package com.example.compose.rally.data.analyze

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.AccountRepository.Companion.accounts
import com.example.compose.rally.data.bill.BillRepository.Companion.bills
import java.time.DayOfWeek


@RequiresApi(Build.VERSION_CODES.O)
fun getBillMCCReport(): String {
    val mccExpenses = calculateBillMCCExpenses()
    val mostSpendingMCC = findMostSpendingMCC()

    return """
        ��� ����� �� ������ �� ������ Merchant Category Code (MCC):
        
${formatMCCExpenses(mccExpenses)}
MCC � ����������� �������: $mostSpendingMCC

���������� �� ������������� ������ ���������!.""".trimIndent()
}

@RequiresApi(Build.VERSION_CODES.O)
private fun calculateBillMCCExpenses(): Map<Int, Double> {
    return bills.filter { it.mcc!=null }
        .groupBy { it.mcc!! }
        .mapValues { (_, mccBills) ->
            mccBills.sumOf { it.amount.toDouble() }
        }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun findMostSpendingMCC(): Int {
    val mccExpenses = bills.filter { it.mcc!=null }
        .groupBy { it.mcc!! }
        .mapValues { (_, mccBills) ->
            mccBills.sumOf { it.amount.toDouble() }
        }
    return mccExpenses.maxByOrNull { it.value }!!.key
}

private fun formatMCCExpenses(mccExpenses: Map<Int, Double>): String {
    val formattedExpenses = StringBuilder()

    for ((mcc, expense) in mccExpenses) {
        val mccString = mcc.toString()
        formattedExpenses.append("MCC $mccString: ����� ��������� ${expense.toInt()} ���\n")
    }
    return formattedExpenses.toString()
}


fun getAccountStatisticReport(): String {
    val totalSum = calculateAccountTotalSum()
    val categoryExpenses = calculateAccountCategoryExpenses()
    val mostSpendingDay = findAccountMostSpendingDay()

    val report = """
    ���� ���������� ���������� �� ��������� ����� �������� ��������� �������:

    ����� ����� �����������: $totalSum


    ����������� �� ����������:
    
${formatCategoryExpenses(categoryExpenses)}
    ����� ���������� ���� ������: ${mostSpendingDay ?: "��� ������"}

    ���������� �� ������������� ������ ���������!.""".trimIndent()

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

