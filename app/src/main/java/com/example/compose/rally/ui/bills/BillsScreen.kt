package com.example.compose.rally.ui.bills

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose.rally.R
import com.example.compose.rally.data.bill.Bill
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.data.category.defaultBillCategories
import com.example.compose.rally.ui.components.BillRow
import com.example.compose.rally.ui.components.StatementBody
import com.example.compose.rally.ui.theme.Ender
import java.util.Locale

/**
 * The Bills screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillsScreen(
    onBillClick: (String) -> Unit = {},
    onAddBillClick: (String) -> Unit = {},
    bills: List<Bill> = remember { BillRepository.bills }
) {
    var billCategoriesState by remember { mutableStateOf(defaultBillCategories) }
    var selectedCategory by remember { mutableStateOf(billCategoriesState.first()) }
    var isFiltering by remember { mutableStateOf(false) }

    val billsTotal = remember(selectedCategory) {
        bills
            .filter { it.category == selectedCategory }
            .sumOf { it.amount.toDouble() }
            .toFloat()
    }

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Расходы" },
        items = if (isFiltering) {
            bills.filter { it.category == selectedCategory }
        } else {
            bills
        },
        amounts = { bill -> bill.amount },
        colors = { bill -> bill.color },
        amountsTotal = if (isFiltering) billsTotal else bills.map { it.amount }.sum(),
        date = { bill -> bill.date },
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

    Spacer(modifier = Modifier.height(16.dp))
    CategoryDropdown(
        categories = billCategoriesState,
        selectedCategory = selectedCategory,
        onCategorySelected = {
            selectedCategory = it
            isFiltering = true
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { onAddBillClick("add_bill") },
            backgroundColor = Ender,
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
    billType: String? = BillRepository.bills.first().name,
    onDeleteBillClick: (Bill) -> Unit = {}
) {
    val bill: Bill = remember(billType) { BillRepository.getBill(billType) }
    Column {
        BillDetailsCard(bill)

        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = bill.billPhoto,
            placeholder = painterResource(R.drawable.enderhead),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            alignment = Alignment.Center
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { onDeleteBillClick(bill) },
            backgroundColor = Ender,
            modifier = Modifier
                .padding(16.dp)
                .semantics { contentDescription = "Удалить account" }
                .align(alignment = Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "Удалить account")
        }
    }
}


@Composable
fun BillDetailsCard(bill: Bill) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = bill.color,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = bill.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    .dropLastWhile { !it.isLetter() },
                modifier = Modifier
                    .align(Alignment.Start),
                maxLines = 1,
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
                    imageVector = Icons.Default.MoneyOff,
                    contentDescription = "Balance Icon"
                )
                Text(text = "Потрачено: ${bill.amount}")
            }

            Spacer(modifier = Modifier.height(58.dp))

            Text(
                text = "Категория: ${bill.category}",
                maxLines = 1,
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Дата: ${bill.stringDate}",
                maxLines = 1,
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }
    }
}
