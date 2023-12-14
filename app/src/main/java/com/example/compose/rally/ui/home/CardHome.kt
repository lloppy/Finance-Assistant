package com.example.compose.rally.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.compose.rally.R
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.ui.components.formatAmount
import com.example.compose.rally.ui.overview.RallyDefaultPadding

@Composable
fun OverviewScreenCard(
    title: String,
    amount: Float,
    modifier: Modifier,
    onClickSeeAll: () -> Unit
) {
    Column(
        modifier = modifier
            .background(colorResource(id = R.color.boxColor))
            .padding(RallyDefaultPadding)
    ) {
        val amountText = "₽" + formatAmount(amount)
        Text(text = amountText, style = MaterialTheme.typography.h5)

        Text(text = "$title", style = MaterialTheme.typography.subtitle2)
        TextButton(
            onClick = onClickSeeAll,

            ) {
            Text(stringResource(R.string.see_all))
        }
    }
}

@Composable
fun AccountsCard(
    onClickSeeAll: () -> Unit
) {
    val amount = AccountRepository.getAllAccounts().map { account -> account.balance }.sum()
    OverviewScreenCard(
        title = stringResource(R.string.accounts),
        amount = amount,
        modifier = Modifier.fillMaxWidth(0.47f),
        onClickSeeAll = onClickSeeAll,
    )
}

/**
 * The Bills card within the Rally Overview screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillsCard(
    onClickSeeAll: () -> Unit
) {
    val amount = BillRepository.bills.map { bill -> bill.amount }.sum()
    OverviewScreenCard(
        title = stringResource(R.string.bills),
        amount = amount,
        modifier = Modifier.fillMaxWidth(1f),
        onClickSeeAll = onClickSeeAll,
    )
}
