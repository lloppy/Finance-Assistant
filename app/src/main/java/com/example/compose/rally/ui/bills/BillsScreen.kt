package com.example.compose.rally.ui.bills

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.Bill
import com.example.compose.rally.data.UserRepository
import com.example.compose.rally.ui.components.BillRow
import com.example.compose.rally.ui.components.StatementBody

/**
 * The Bills screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillsScreen(
    onBillClick: (String) -> Unit = {},
    onAddBillClick: (String) -> Unit = {},
    bills: List<Bill> = remember { UserRepository.bills }
) {
    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Расходы" },
        items = bills,
        amounts = { bill -> bill.amount },
        colors = { bill -> bill.color },
        amountsTotal = bills.map { bill -> bill.amount }.sum(),
        circleLabel = stringResource(R.string.due),
        rows = { bill ->
            BillRow(
                modifier = Modifier.clickable {
                    onBillClick(bill.name)
                },
                name = bill.name,
                stringDate = bill.stringDate,
                category = bill.category,
                amount = bill.amount,
                color = bill.color
            )
        }
    )
    Box(modifier = Modifier.fillMaxSize()) {

        FloatingActionButton(
            onClick = { onAddBillClick("add_bill") },
            modifier = Modifier
                .padding(16.dp)
                .semantics { contentDescription = "Добавить запись" }
                .align(alignment = Alignment.BottomEnd)

        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить запись")
        }
    }
}


/**
 * Detail screen for a single Bill.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleBillScreen(
    billType: String? = UserRepository.bills.first().name
) {
    val bill: Bill = remember(billType) { UserRepository.getBill(billType) }
    StatementBody(
        items = listOf(bill),
        colors = { bill.color },
        amounts = { bill.amount },
        amountsTotal = bill.amount,     //?????????
        circleLabel = bill.name,
    ) { row ->
        BillRow(
            name = row.name,
            stringDate = row.stringDate,
            category = row.category,
            amount = row.amount,
            color = row.color
        )
    }
}
