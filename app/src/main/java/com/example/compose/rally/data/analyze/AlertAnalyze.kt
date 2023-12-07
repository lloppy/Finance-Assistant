package com.example.compose.rally.data.analyze

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import java.time.LocalDate
import kotlin.math.roundToInt


@RequiresApi(Build.VERSION_CODES.O)
fun calculateBalance(): Float {
    val totalBalance = AccountRepository.accounts
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.balance.toDouble() }.toFloat()
    val totalSpend = BillRepository.bills
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.amount.toDouble() }.toFloat()

    return totalBalance - totalSpend
}

@RequiresApi(Build.VERSION_CODES.O)
fun analyzeAlert(): String {
    val totalBalance = AccountRepository.accounts
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.balance.toDouble() }.toFloat()
    val totalSpend = BillRepository.bills
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.amount.toDouble() }.toFloat()
    val percent = ((totalSpend / totalBalance) * 100).roundToInt()

    return when {
        percent > 150 -> "Осторожно!\nВы многократно превысили бюджет!"
        percent <= 150 && totalBalance / 2 < totalSpend -> "Предупреждение!\nВы израсходовали более $percent% вашего бюджета"
        percent <= 150 -> "Ваши траты под контролем!"
        else -> "Вы израсходовали $percent% вашего бюджета на покупки в этом месяце"
    }
}

