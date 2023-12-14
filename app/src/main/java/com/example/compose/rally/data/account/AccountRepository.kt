package com.example.compose.rally.data.account

import android.os.Build
import android.provider.ContactsContract.Settings.getDefaultAccount
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class AccountRepository {
    companion object {

        private var accounts: List<Account> = listOf(
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

        fun getAccount(accountName: String?): Account {
            return try {
                accounts.first { it.name == accountName }
            } catch (e: NoSuchElementException) {
                accounts[1]
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun addAccount(account: Account) {
            accounts += account
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
    }
}