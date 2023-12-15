package com.example.compose.rally.data.util.csv

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.compose.rally.R
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.Bill
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.data.category.defaultAccountCategories
import com.example.compose.rally.data.category.defaultBillCategories
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset.*
import java.time.LocalDateTime

//* 1. "Дата операции";
//  2. "Дата платежа";
//  3. "Номер карты";
//  4. "Статус";
//  5. "Сумма операции";
//  6. "Валюта операции";
//  7. "Сумма платежа";
//  8. "Валюта платежа";
//  9. "Кэшбэк";
//  10. "Категория";
//  11. "MCC";
//  12. "Описание";
//  13. "Бонусы (включая кэшбэк)";
//  14. "Округление на инвесткопилку";
//  15. "Сумма операции с округлением"/

@RequiresApi(Build.VERSION_CODES.O)
fun handleCSVFile(context: Context, uri: Uri) {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream, forName("Windows-1251")))

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            var cleanLine = line!!.replace("\"", "")
            var groups = cleanLine.split(";")

            Log.e("csv", "${groups[4].first()} date is ${groups[0]} ")
            if (groups[4].first() == '-') {
                takeExpenseSplit(groups, context)
            } else if (groups[4].first().isDigit()) {
                takeIncomeSplit(groups)
            }
        }
        reader.close()
        inputStream?.close()
        Toast.makeText(context, context.getString(R.string.read_sucsess), Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, context.getString(R.string.error_occured), Toast.LENGTH_SHORT)
            .show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun takeIncomeSplit(group: List<String>) {
    val regex = "^(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):\\d{2}".toRegex()
    val matchResult = regex.find(group[0])

    val (day, month, year, hour, minute) = matchResult!!.destructured
    val balance = group[14].split(",")

    if (!defaultAccountCategories.contains(group[9])) {
        defaultAccountCategories += group[9]
    }

    var name = group[11]
    var counter = 0

    while (AccountRepository.accounts.any { account -> account.name == name }) {
        name = "${group[11]} ${++counter}"
    }

    AccountRepository.addAccount(
        Account(
            name = name,
            date = LocalDateTime.of(
                year.toInt(),
                month.toInt(),
                day.toInt(),
                hour.toInt(),
                minute.toInt()
            ),
            timesRepeat = 0,
            cardNumber = if (group[2].isNullOrEmpty()) 0 else group[2].replace("*", "").toInt(),
            balance = balance[0].toFloat() + (balance[1].toFloat() / 100),
            category = group[9]
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun takeExpenseSplit(group: List<String>, context: Context) {
    val regex = "^(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):\\d{2}".toRegex()
    val matchResult = regex.find(group[0])

    val (day, month, year, hour, minute) = matchResult!!.destructured
    val amount = group[14].split(",")

    if (!defaultBillCategories.contains(group[9])) {
        defaultBillCategories += group[9]
    }

    var name = group[11]
    var counter = 0

    while (BillRepository.bills.any { bill -> bill.name == name }) {
        name = "${group[11]} ${++counter}"
    }

    BillRepository.addBill(
        Bill(
            name = name,
            date = LocalDateTime.of(
                year.toInt(),
                month.toInt(),
                day.toInt(),
                hour.toInt(),
                minute.toInt()
            ),
            timesRepeat = 0,
            billPhoto = null,
            category = group[9],
            mcc = if (group[10].isNullOrEmpty()) null else group[10].toInt(),
            amount = amount[0].toFloat() + (amount[1].toFloat() / 100),
        ),
        context = context
    )
}
