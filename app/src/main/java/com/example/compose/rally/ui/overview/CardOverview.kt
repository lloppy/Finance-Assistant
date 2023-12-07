package com.example.compose.rally.ui.overview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.ui.components.AccountRow
import com.example.compose.rally.ui.components.BillRow
import com.example.compose.rally.ui.components.formatAmount


/**
 * Base structure for cards in the Overview screen.
 */
@Composable
fun <T> OverviewScreenCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    Card {
        Column {
            Column(Modifier.padding(RallyDefaultPadding)) {
                Text(text = title, style = MaterialTheme.typography.subtitle2)
                val amountText = stringResource(R.string.ruble) + formatAmount(
                    amount
                )
                Text(text = amountText, style = MaterialTheme.typography.h2)
            }
            OverViewDivider(data, values, colors)
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                data.take(SHOWN_ITEMS).forEach { row(it) }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "$title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}

/**
 * The Accounts card within the Rally Overview screen.
 */
@Composable
fun AccountsCard(
    onClickSeeAll: () -> Unit,
    onAccountClick: (String) -> Unit,
) {
    val amount = AccountRepository.accounts.map { account -> account.balance }.sum()
    OverviewScreenCard(
        title = stringResource(R.string.accounts),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = AccountRepository.accounts,
        colors = { it.color },
        values = { it.balance }
    ) { account ->
        AccountRow(
            modifier = Modifier.clickable { onAccountClick(account.name) },
            name = account.name,
            stringDate = account.stringDate,
            // number = account.cardNumber,
            category = account.category,
            amount = account.balance,
            color = account.color
        )
    }
}

/**
 * The Bills card within the Rally Overview screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillsCard(
    onClickSeeAll: () -> Unit,
    onBillClick: (String) -> Unit,
) {
    val amount = BillRepository.bills.map { bill -> bill.amount }.sum()
    OverviewScreenCard(
        title = stringResource(R.string.bills),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = BillRepository.bills,
        colors = { it.color },
        values = { it.amount }
    ) { bill ->
        BillRow(
            modifier = Modifier.clickable { onBillClick(bill.name) },
            name = bill.name,
            stringDate = bill.stringDate,
            category = bill.category,
            amount = bill.amount,
            color = bill.color
        )
    }
}
