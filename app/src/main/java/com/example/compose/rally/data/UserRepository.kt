package com.example.compose.rally.data

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Files
import java.time.LocalDateTime
import java.util.regex.Matcher
import java.util.regex.Pattern

object UserRepository {
    var accounts: List<Account> = listOf(
        Account(
            "add_account",
            LocalDateTime.now(),
            LocalDateTime.now(),
            0,
            3456,
            253f,
            "Default"
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    var bills: List<Bill> = listOf(
        Bill(
            "add_bill",
            LocalDateTime.now(),
            LocalDateTime.now(),
            0,
            "Default",
            9f,
        )
    )

    val accountCategories: List<String> =
        listOf("Зарплата", "Стипендия", "Инвестиции", "Подработка")

    val billCategories: List<String> = listOf(
        "Квартира", "ЖКХ", "Продукты", "Одежда и обувь",
        "Здоровье", "Интернет и телефон", "Здоровье", "Другое",
    )

    fun getAccount(accountName: String?): Account {
        return accounts.first { it.name == accountName }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBill(billName: String?): Bill {
        return bills.first { it.name == billName }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addAccount(account: Account) {
        accounts += account
        //   saveFile(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addBill(bill: Bill, context: Context) {
        if ((bill.name != bills.last().name && bill.amount != bills.last().amount)
            || (bill.category != "QR")
        ) {
            bills += bill
            Toast.makeText(context, "Покупка добавлена!", Toast.LENGTH_SHORT).show()
        }
        //   saveFile(context)
    }

    fun removeAccount(account: Account) {
        accounts = accounts.filterNot {
            it.name == account.name
                    && it.stringDate == account.stringDate
                    && it.balance == account.balance
                    && it.category == account.category
                    && it.cardNumber == account.cardNumber
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun removeBill(bill: Bill) {
        bills = bills.filterNot {
            it.name == bill.name
                    && it.stringDate == bill.stringDate
                    && it.amount == bill.amount
                    && it.category == bill.category
        }
    }

    fun saveFile(context: Context) {
        val fileName = "accounts.txt"

        try {
            val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val writer = PrintWriter(fileOutputStream.bufferedWriter())

            for (account: Account in accounts) {
                writer.println("${account.name}:${account.cardNumber}:${account.balance}")
            }
            writer.close()

        } catch (e: IOException) {
            Log.e("database", "database saveFile error")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun readFile(context: Context) {
        val filePath = File(context.filesDir, "accounts.txt")
        Log.e("database", "filePath is $filePath")

        try {
            val newAccounts = Files.readAllLines(filePath.toPath())
                .map { it.split(":") }
                .filter { it.size == 7 }
                // нужно проверить эту строчку  - LocalDateTime.parse(it[1]) может крашнуться
                .map {
                    Account(
                        it[0],
                        LocalDateTime.parse(it[1]),
                        LocalDateTime.parse(it[2]),
                        it[3].toInt(),
                        it[4].toInt(),
                        it[5].toFloat(),
                        it[6]
                    )
                }
            accounts = newAccounts

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NewApi")
    public fun createBillFromQR(result: String): Bill {
        val pattern = "t=(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})&s=(.*)\\.(.*)&fn"
        val matcher: Matcher = Pattern.compile(pattern).matcher(result)

        if (matcher.find()) {
            return Bill(
                name = "Покупка " + matcher.group(3) + "." + matcher.group(2),
                date = LocalDateTime.of(
                    matcher.group(1)!!.toInt(),
                    matcher.group(2)!!.toInt(),
                    matcher.group(3)!!.toInt(),
                    matcher.group(4)!!.toInt(),
                    matcher.group(5)!!.toInt()
                ),
                dateRepeat = LocalDateTime.now(),
                timesRepeat = 0,
                category = "QR",
                amount = (matcher.group(6)!!.toFloat()) + (matcher.group(7)!!.toFloat() / 100)
            )
        }
        throw IllegalArgumentException("Invalid QR code format")
    }
}