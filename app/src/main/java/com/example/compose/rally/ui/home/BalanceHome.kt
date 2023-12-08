package com.example.compose.rally.ui.home

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.compose.rally.R
import com.example.compose.rally.data.analyze.calculateBalance
import com.example.compose.rally.ui.components.formatAmount
import com.example.compose.rally.ui.overview.RallyDefaultPadding

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Balance(
    modifier: Modifier,
    context: Context,
    onAddBillClick: () -> Unit = {},
    amountsTotal: Float,
    billTotal: Float,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.boxColor))
            .aspectRatio(1.2f)
    ) {

        Column(
            modifier = modifier
                .padding(RallyDefaultPadding)
                .background(colorResource(id = R.color.boxColor))
                .weight(0.8f)

        ) {
            val amountText = stringResource(R.string.ruble) + formatAmount(calculateBalance(amountsTotal, billTotal))
            Text(text = amountText, style = MaterialTheme.typography.h2)

            Text(
                text = stringResource(R.string.balance_title),
                style = MaterialTheme.typography.subtitle2
            )
        }

        Column(
            modifier = modifier
                .fillMaxHeight()
                .weight(1.1f)
                .background(colorResource(id = R.color.boxColor)),
            verticalArrangement = Arrangement.Bottom,
        ) {
            addSpend(
                context = context,
                onAddBillClick = onAddBillClick,
            )
        }
    }
}
