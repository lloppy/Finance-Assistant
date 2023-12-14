package com.example.compose.rally.data.util.database

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun saveAccountsToFile(context: Context) {
    val fileName = "accounts_data.json"
    val accountsJson = Gson().toJson(AccountRepository.getAllAccounts())
    try {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(accountsJson.toByteArray())
        }
        Log.e("database", "saveAccountsToFile done")
    } catch (e: IOException) {
        Log.e("database", "saveAccountsToFile error")
        e.printStackTrace()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun readAccountsFromFile(context: Context): List<Account> {
    val fileName = "accounts_data.json"
    var accounts: List<Account> = emptyList()

    try {
        context.openFileInput(fileName).use {
            val size = it.available()
            val buffer = ByteArray(size)
            it.read(buffer)
            val json = String(buffer, Charsets.UTF_8)

            accounts = Gson().fromJson(json, object : TypeToken<List<Account>>() {}.type)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return accounts
}

