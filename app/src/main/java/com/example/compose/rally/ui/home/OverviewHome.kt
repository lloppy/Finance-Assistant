package com.example.compose.rally.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.ui.overview.AccountsCard
import com.example.compose.rally.ui.overview.BillsCard
import com.example.compose.rally.ui.overview.RallyDefaultPadding

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OverviewHome(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
) {
    Column(
        modifier = Modifier.semantics { contentDescription = "Обзор" }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AccountsCard(
                onClickSeeAll = onClickSeeAllAccounts,
            )

            Spacer(modifier = Modifier.width(RallyDefaultPadding))

            BillsCard(
                onClickSeeAll = onClickSeeAllBills,
            )
        }
        Spacer(Modifier.height(RallyDefaultPadding))
    }
}