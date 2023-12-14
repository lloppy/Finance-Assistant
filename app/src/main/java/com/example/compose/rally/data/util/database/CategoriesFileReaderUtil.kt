package com.example.compose.rally.data.util.database

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.category.defaultAccountCategories
import com.example.compose.rally.data.category.defaultBillCategories
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun saveCategoriesToFile(context: Context) {
    val categoriesMap = mapOf(
        "defaultAccountCategories" to defaultAccountCategories,
        "defaultBillCategories" to defaultBillCategories
    )

    val fileName = "categories_data.json"
    val categoriesJson = Gson().toJson(categoriesMap)

    try {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(categoriesJson.toByteArray())
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun readCategoriesFromFile(context: Context) {
    val fileName = "categories_data.json"

    try {
        context.openFileInput(fileName).use {
            val size = it.available()
            val buffer = ByteArray(size)
            it.read(buffer)
            val json = String(buffer, Charsets.UTF_8)

            val categoriesMap: Map<String, List<String>> =
                Gson().fromJson(json, object : TypeToken<Map<String, List<String>>>() {}.type)

            defaultAccountCategories = categoriesMap["defaultAccountCategories"] ?: emptyList()
            defaultBillCategories = categoriesMap["defaultBillCategories"] ?: emptyList()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}