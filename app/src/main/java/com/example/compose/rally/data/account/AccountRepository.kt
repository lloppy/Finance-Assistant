package com.example.compose.rally.data.account

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import kotlin.math.ceil

class AccountRepository {
    companion object {

        var accounts: List<Account> = listOf(
            Account(
                "add_account",
                LocalDateTime.now(),
                0,
                3456,
                0.000001f,
                "Default"
            )
        )

        fun getAllAccounts(): List<Account> {
            return accounts
        }

        fun setFileAccounts(accountsList: List<Account>) {
            accounts = accountsList
        }

        fun getAccount(accountId: String?): Account {
            return accounts.first { it.id == accountId }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun addAccount(account: Account) {
            var name = account.name
            var counter = 0

            while (accounts.any { it.name == name }) {
                name = "$name ${++counter}"
            }

            // listOf("Только один день", "Каждый день", "Каждую неделю", "Каждый месяц")
            when (account.timesRepeat) {
                0 -> {
                    accounts += account.copy(name = name)
                }
                1 -> {
                    for (n in 0..30 - account.date.dayOfMonth) {
                        while (accounts.any { it.name == name }) {
                            name = "$name ${++counter}"
                        }
                        accounts += Account(
                            name = name,
                            date = account.date.plusDays(n.toLong()),
                            timesRepeat = 0,
                            cardNumber = account.cardNumber,
                            balance = account.balance,
                            category = account.category
                        )
                    }
                }
                2 -> {
                    for (n in 0 .. ceil((30 - account.date.dayOfMonth) / 7.0).toInt()) {
                        while (accounts.any { it.name == name }) {
                            name = "$name ${++counter}"
                        }
                        accounts += Account(
                            name = name,
                            date = account.date.plusWeeks(n.toLong()),
                            timesRepeat = 0,
                            cardNumber = account.cardNumber,
                            balance = account.balance,
                            category = account.category
                        )
                    }
                }
                3 -> {
                    for (n in 0 until ceil((30 - account.date.dayOfMonth) / 30.0).toInt()) {
                        while (accounts.any { it.name == name }) {
                            name = "$name ${++counter}"
                        }
                        accounts += Account(
                            name = name,
                            date = account.date.plusMonths(n.toLong()),
                            timesRepeat = 0,
                            cardNumber = account.cardNumber,
                            balance = account.balance,
                            category = account.category
                        )
                    }
                }
            }
        }

        fun removeAccount(account: Account) {
            accounts = accounts.filterNot {
                it.name == account.name
                        && it.stringDate == account.stringDate
                        && it.timesRepeat == account.timesRepeat
                        && it.cardNumber == account.cardNumber
                        && it.balance == account.balance
                        && it.category == account.category
            }
        }
    }
}