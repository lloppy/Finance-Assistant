package com.example.compose.rally.data.analyze

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import java.time.LocalDate
import kotlin.math.ceil
import kotlin.math.roundToInt


@RequiresApi(Build.VERSION_CODES.O)
fun calculateBalance(amountsTotal: Float, billTotal: Float): Float {
    return amountsTotal - billTotal
}

@RequiresApi(Build.VERSION_CODES.O)
fun analyzeAlert(): String {
    val totalBalance = AccountRepository.getAllAccounts()
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.balance.toDouble() }.toFloat()
    val totalSpend = BillRepository.bills
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.amount.toDouble() }.toFloat()

    val percent =
        if (totalSpend == 0f || totalBalance == 0f) 0 else ((totalSpend / totalBalance) * 100).roundToInt()

    return when {
        percent > 150 -> "Осторожно!\nВы многократно превысили бюджет!"
        percent <= 150 && totalBalance / 2 < totalSpend && ceil(totalBalance) != 1f -> "Предупреждение!\nВы израсходовали более $percent% вашего бюджета"
        percent <= 150 -> "Ваши траты под контролем!"
        else -> "Вы израсходовали $percent% вашего бюджета на покупки в этом месяце"
    }
}

