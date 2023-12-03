package com.example.compose.rally.ui.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.Account
import com.example.compose.rally.data.UserRepository
import com.example.compose.rally.ui.components.AccountRow
import com.example.compose.rally.ui.components.StatementBody

/**
 * The Accounts screen.
 */
@Composable
fun AccountsScreen(
    onAccountClick: (String) -> Unit = {},
    onAddAccountClick: (String) -> Unit = {}
) {
    val amountsTotal = remember { UserRepository.accounts.map { account -> account.balance }.sum() }
    StatementBody(
        modifier = Modifier.semantics { contentDescription = "Счета" },
        items = UserRepository.accounts,
        amounts = { account -> account.balance },
        colors = { account -> account.color },
        amountsTotal = amountsTotal,
        circleLabel = stringResource(R.string.total),
        rows = { account ->
            AccountRow(
                modifier = Modifier.clickable {
                    onAccountClick(account.name)
                },
                name = account.name,
                stringDate = account.stringDate,
                //number = account.cardNumber,
                category = account.category,
                amount = account.balance,
                color = account.color
            )
        }
    )
    Box(modifier = Modifier.fillMaxSize()) {

        FloatingActionButton(
            onClick = { onAddAccountClick("add_account") },
            modifier = Modifier
                .padding(16.dp)
                .semantics { contentDescription = "Добавить счет" }
                .align(alignment = Alignment.BottomEnd)

        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить счет")
        }
    }
}

/**
 * Detail screen for a single account.
 */
@Composable
fun SingleAccountScreen(
    accountType: String? = UserRepository.accounts.first().name,
    onDeleteAccountClick: (Account) -> Unit = {}
) {
    val account = remember(accountType) { UserRepository.getAccount(accountType) }
    StatementBody(
        items = listOf(account),
        colors = { account.color },
        amounts = { account.balance },
        amountsTotal = account.balance,
        circleLabel = account.name,
    ) { row ->
        AccountRow(
            name = row.name,
            stringDate = account.stringDate,
            // number = row.cardNumber,
            category = row.category,
            amount = row.balance,
            color = row.color
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { onDeleteAccountClick(account) },
            modifier = Modifier
                .padding(16.dp)
                .semantics { contentDescription = "Удалить account" }
                .align(alignment = Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "Удалить account")
        }
    }
}
