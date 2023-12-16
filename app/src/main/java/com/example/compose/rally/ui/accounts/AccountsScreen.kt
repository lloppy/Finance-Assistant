package com.example.compose.rally.ui.accounts

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.rally.Accounts
import com.example.compose.rally.R
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.category.defaultAccountCategories
import com.example.compose.rally.navigateSingleTopTo
import com.example.compose.rally.ui.components.AccountRow
import com.example.compose.rally.ui.components.StatementBody
import com.example.compose.rally.ui.theme.Ender
import java.util.Locale

/**
 * The Accounts screen.
 */
@Composable
fun AccountsScreen(
    onAccountClick: (String) -> Unit = {},
    onAddAccountClick: (String) -> Unit = {}
) {
    val amountsTotal = remember {
        AccountRepository.getAllAccounts().map { account -> account.balance }.sum()
    }
    val accountCategoriesState by remember { mutableStateOf(defaultAccountCategories) }
    var selectedCategory by remember { mutableStateOf(accountCategoriesState.first()) }
    var isFiltering by remember { mutableStateOf(false) }
    val selectedCategoryTotal = remember(selectedCategory) {
        // Calculate the total for the selected category
        AccountRepository.getAllAccounts()
            .filter { it.category == selectedCategory }
            .sumOf { it.balance.toDouble() }
            .toFloat()
    }

    StatementBody(
        modifier = Modifier.semantics { contentDescription = "Счета" },
        items = if (isFiltering) {
            AccountRepository.getAllAccounts().filter { it.category == selectedCategory }
        } else {
            AccountRepository.getAllAccounts()
        },
        amounts = { account -> account.balance },
        date = { account -> account.date },
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
            backgroundColor = Ender,
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
    accountType: String? = AccountRepository.getAllAccounts().first().name,
    onDeleteAccountClick: (Account) -> Unit = {},
) {
    val account = remember(accountType) { AccountRepository.getAccount(accountType) }
    AccountDetailsCard(account = account)

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { onDeleteAccountClick(account) },
            backgroundColor = Ender,
            modifier = Modifier
                .padding(16.dp)
                .semantics { contentDescription = "Удалить account" }
                .align(alignment = Alignment.BottomEnd),
        ) {
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Удалить account",
            )
        }
    }
}


@Composable
fun AccountDetailsCard(account: Account) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = account.color,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = account.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                modifier = Modifier
                    .align(Alignment.Start),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = "Balance Icon"
                )
                Text(text = "Зачислено: ${account.balance}")
            }

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Категория: ${account.category}",
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Дата: ${account.stringDate}",
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }
    }
}
