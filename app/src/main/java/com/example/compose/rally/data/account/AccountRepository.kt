package com.example.compose.rally.data.account

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class AccountRepository {
    companion object {
        var accounts: List<Account> = listOf(
            Account(
                "add_account",
                LocalDateTime.now(),
                0,
                3456,
                253f,
                "Default"
            )
        )

        fun getAccount(accountName: String?): Account {
            return accounts.first { it.name == accountName }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun addAccount(account: Account) {
            accounts += account
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
    }
}