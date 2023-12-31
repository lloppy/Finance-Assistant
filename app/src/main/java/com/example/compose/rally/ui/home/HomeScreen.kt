package com.example.compose.rally.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.ui.overview.RallyDefaultPadding

/**
 * The Home screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAddBillClick: () -> Unit = {},
    onClickAnalyze: () -> Unit = {}
) {
    val context = LocalContext.current
    val amountsTotal =
        remember { AccountRepository.getAllAccounts().map { account -> account.balance }.sum() }
    val billTotal = remember { BillRepository.bills.map { bill -> bill.amount }.sum() }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "" }
    ) {

        // Text(stringResource(R.string.current_category, database.spendingGoal)

        Balance(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = RallyDefaultPadding)
                .fillMaxHeight(0.4f),
            context = context,
            onAddBillClick = onAddBillClick,
            amountsTotal = amountsTotal,
            billTotal = billTotal,
        )

        OverviewHome(
            onClickSeeAllAccounts = onClickSeeAllAccounts,
            onClickSeeAllBills = onClickSeeAllBills,
        )

        AlertHome(onClickAnalyze = onClickAnalyze, context)
        Spacer(Modifier.height(RallyDefaultPadding))
    }
}