package com.example.compose.rally.data.util.csv

import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.compose.rally.R
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.Bill
import com.example.compose.rally.data.bill.BillRepository
import org.mozilla.universalchardet.UniversalDetector
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.charset.Charset.*
import java.time.LocalDateTime

//* 1. "���� ��������";
//  2. "���� �������";
//  3. "����� �����";
//  4. "������";
//  5. "����� ��������";
//  6. "������ ��������";
//  7. "����� �������";
//  8. "������ �������";
//  9. "������";
//  10. "���������";
//  11. "MCC";
//  12. "��������";
//  13. "������ (������� ������)";
//  14. "���������� �� �������������";
//  15. "����� �������� � �����������"/

@RequiresApi(Build.VERSION_CODES.O)
fun handleCSVFile(context: Context, uri: Uri) {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)

        val reader = BufferedReader(InputStreamReader(inputStream, forName("Windows-1251")))

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            var cleanLine = line!!.replace("\"", "")
            var groups = cleanLine.split(";")


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
        Toast.makeText(context, context.getString(R.string.error_occured), Toast.LENGTH_SHORT).show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun takeIncomeSplit(group: List<String>) {
    val regex = "^(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):\\d{2}".toRegex()
    val matchResult = regex.find(group[0])

    val (day, month, year, hour, minute) = matchResult!!.destructured
    val balance = group[14].split(",")

    AccountRepository.addAccount(
        Account(
            name = group[11],
            date = LocalDateTime.of(
                year.toInt(),
                month.toInt(),
                day.toInt(),
                hour.toInt(),
                minute.toInt()
            ),
            timesRepeat = 0,
            cardNumber = group[2].replace("*", "").toInt(),
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

    BillRepository.addBill(
        Bill(
            name = group[11],
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
            amount = amount[0].toFloat() + (amount[1].toFloat() / 100),
        ),
        context = context
    )
}
