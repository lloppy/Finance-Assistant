package com.example.compose.rally.ui.overview

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.example.compose.rally.R
import com.example.compose.rally.data.UserRepository
import com.example.compose.rally.scan.QRCodeScannerActivity
import com.example.compose.rally.ui.components.AccountRow
import com.example.compose.rally.ui.components.BillRow
import com.example.compose.rally.ui.components.RallyAlertDialog
import com.example.compose.rally.ui.components.RallyDivider
import com.example.compose.rally.ui.components.formatAmount
import java.time.LocalDate
import java.util.Locale
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OverviewScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
    onBillClick: (String) -> Unit = {},
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Обзор" }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.boxColor)),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(
                modifier = Modifier
                    .width(RallyDefaultPadding)
                    .background(colorResource(id = R.color.boxBackground)),
            )
            Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = "Camera",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(64.dp)
                    .clickable {
                        val intent = Intent(context, QRCodeScannerActivity::class.java)
                        val options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity)
                        context.startActivity(intent, options.toBundle())
                    }
            )
            Spacer(
                Modifier
                    .width(RallyDefaultPadding)
                    .background(colorResource(id = R.color.boxBackground)),
            )
            AlertCard()
        }
        Spacer(Modifier.height(RallyDefaultPadding))
        AccountsCard(
            onClickSeeAll = onClickSeeAllAccounts,
            onAccountClick = onAccountClick
        )
        Spacer(Modifier.height(RallyDefaultPadding))
        BillsCard(
            onClickSeeAll = onClickSeeAllBills,
            onBillClick = onBillClick
        )
    }
}

/**
 * The Alerts card within the Rally Overview screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AlertCard() {
    var showDialog by remember { mutableStateOf(false) }
    val alertMessage: String

    val totalBalance = UserRepository.accounts
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.balance.toDouble() }.toFloat()
    val totalSpend = UserRepository.bills
        .filter { it.date.month == LocalDate.now().month }
        .sumOf { it.amount.toDouble() }.toFloat()
    val percent = ((totalSpend / totalBalance) * 100).roundToInt()

    if (percent > 150) {
        alertMessage = "Осторожно!\nВы многократно превысили бюджет!";
    } else if (percent <= 150 && totalBalance / 2 < totalSpend) {
        alertMessage = "Предупреждение!\nВы израсходовали более $percent% вашего бюджета.";
    } else if (percent <= 150) {
        alertMessage = "Ваши траты под контролем!";
    } else {
        alertMessage = "Вы израсходовали $percent% вашего бюджета на покупки в этом месяце.";
    }

    Log.e("spend", "totalSpend / totalBalance ${totalSpend / totalBalance}")
    Log.e("spend", "totalSpend / totalBalance $percent")
    Log.e("spend", "totalBalance / totalSpend  ${(totalBalance / totalSpend).roundToInt()}")

    if (showDialog) {
        RallyAlertDialog(
            onDismiss = {
                showDialog = false
            },
            bodyText = alertMessage,
            buttonText = "Закрыть".uppercase(Locale.getDefault())
        )
    }
    Card {
        Column {
            AlertHeader {
                showDialog = true
            }
            RallyDivider(
                modifier = Modifier.padding(start = RallyDefaultPadding, end = RallyDefaultPadding)
            )
            AlertItem(alertMessage)
        }
    }
}

@Composable
private fun AlertHeader(onClickSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.alert_title),
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = onClickSeeAll,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(id = R.string.see_all),
                style = MaterialTheme.typography.button,
            )
        }
    }
}

@Composable
private fun AlertItem(message: String) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            // Regard the whole row as one semantics node. This way each row will receive focus as
            // a whole and the focus bounds will be around the whole row content. The semantics
            // properties of the descendants will be merged. If we'd use clearAndSetSemantics instead,
            // we'd have to define the semantics properties explicitly.
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = MaterialTheme.typography.body2,
            modifier = Modifier.weight(1f),
            text = message
        )
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.Top)
                .clearAndSetSemantics {}
        ) {
            Icon(Icons.Filled.Sort, contentDescription = null)
        }
    }
}

/**
 * Base structure for cards in the Overview screen.
 */
@Composable
private fun <T> OverviewScreenCard(
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
                val amountText = "₽" + formatAmount(
                    amount
                )
                Text(text = amountText, style = MaterialTheme.typography.h2)
            }
            OverViewDivider(data, values, colors)
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                data.take(SHOWN_ITEMS).forEach { row(it) }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "Все $title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}

@Composable
private fun <T> OverViewDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color
) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

/**
 * The Accounts card within the Rally Overview screen.
 */
@Composable
private fun AccountsCard(onClickSeeAll: () -> Unit, onAccountClick: (String) -> Unit) {
    val amount = UserRepository.accounts.map { account -> account.balance }.sum()
    OverviewScreenCard(
        title = stringResource(R.string.accounts),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = UserRepository.accounts,
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
private fun BillsCard(onClickSeeAll: () -> Unit, onBillClick: (String) -> Unit) {
    val amount = UserRepository.bills.map { bill -> bill.amount }.sum()
    OverviewScreenCard(
        title = stringResource(R.string.bills),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = UserRepository.bills,
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

@Composable
private fun SeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.see_all))
    }
}


private val RallyDefaultPadding = 12.dp

private const val SHOWN_ITEMS = 3
