package com.example.compose.rally.ui.accounts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.category.defaultAccountCategories
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
    val amountsTotal = remember {
        AccountRepository.accounts.map { account -> account.balance }.sum()
    }
    var accountCategoriesState by remember { mutableStateOf(defaultAccountCategories) }
    var selectedCategory by remember { mutableStateOf(accountCategoriesState.first()) }
    var isFiltering by remember { mutableStateOf(false) }
    val selectedCategoryTotal = remember(selectedCategory) {
        // Calculate the total for the selected category
        AccountRepository.accounts
            .filter { it.category == selectedCategory }
            .sumOf { it.balance.toDouble() }
            .toFloat()
    }

    StatementBody(
        modifier = Modifier.semantics { contentDescription = "Счета" },
        items = if (isFiltering) {
            AccountRepository.accounts.filter { it.category == selectedCategory }
        } else {
            AccountRepository.accounts
        },
        amounts = { account -> account.balance },
        colors = { account -> account.color },
        amountsTotal = if (isFiltering) selectedCategoryTotal else amountsTotal,
        circleLabel = if (isFiltering) selectedCategory else stringResource(R.string.total),
        rows = { account ->
            AccountRow(
                modifier = Modifier.clickable {
                    onAccountClick(account.name)
                },
                name = account.name,
                stringDate = account.stringDate,
                category = account.category,
                amount = account.balance,
                color = account.color
            )
        }
    )

    CategoryDropdown(
        categories = accountCategoriesState,
        selectedCategory = selectedCategory,
        onCategorySelected = {
            selectedCategory = it
            isFiltering = true
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
    accountType: String? = AccountRepository.accounts.first().name,
    onDeleteAccountClick: (Account) -> Unit = {}
) {
    val account = remember(accountType) { AccountRepository.getAccount(accountType) }
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
