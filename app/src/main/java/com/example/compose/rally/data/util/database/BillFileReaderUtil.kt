package com.example.compose.rally.data.util.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.bill.Bill
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.data.bill.BillRepository.Companion.getAllBills
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.O)
fun saveBillsToFile(context: Context) {
    val fileName = "bills_data.json"
    val billsJson = Gson().toJson(getAllBills())

    try {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(billsJson.toByteArray())
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun readBillsFromFile(context: Context): List<Bill> {
    val fileName = "bills_data.json"
    var bills: List<Bill> = getAllBills()

    try {
        context.openFileInput(fileName).use {
            val size = it.available()
            val buffer = ByteArray(size)
            it.read(buffer)
            val json = String(buffer, Charsets.UTF_8)

            bills = Gson().fromJson(json, object : TypeToken<List<Bill>>() {}.type)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bills
}

