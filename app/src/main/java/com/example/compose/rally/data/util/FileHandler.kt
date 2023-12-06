package com.example.compose.rally.data.util

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Files
import java.time.LocalDateTime

class FileHandler {
    companion object {
        fun saveFile(context: Context) {
            val fileName = "accounts.txt"

            try {
                val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
                val writer = PrintWriter(fileOutputStream.bufferedWriter())

                for (account: Account in AccountRepository.accounts) {
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
                    .filter { it.size == 6 }
                    // нужно проверить эту строчку  - LocalDateTime.parse(it[1]) может крашнуться
                    .map {
                        Account(
                            it[0],
                            LocalDateTime.parse(it[1]),
                            it[2].toInt(),
                            it[3].toInt(),
                            it[4].toFloat(),
                            it[5]
                        )
                    }
                AccountRepository.accounts = newAccounts

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}