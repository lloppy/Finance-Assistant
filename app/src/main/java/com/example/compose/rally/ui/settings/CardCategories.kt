package com.example.compose.rally.ui.settings

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.data.category.defaultAccountCategories
import com.example.compose.rally.data.category.defaultBillCategories
import com.example.compose.rally.ui.components.formatAmount
import com.example.compose.rally.ui.overview.RallyDefaultPadding

@Composable
fun OverviewCategoriesCard(
    title: String,
    categoryName: String,
    categoryCount: Int,
    modifier: Modifier,
    onClickAddCategories: () -> Unit
) {
    Column(
        modifier = modifier
            .height(200.dp)
            .background(colorResource(id = R.color.boxColor))
            .padding(RallyDefaultPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$title\n\nВсего категорий: $categoryCount", style = MaterialTheme.typography.subtitle2)
        TextButton(onClick = onClickAddCategories) {
            Text(categoryName)
        }
    }
}

@Composable
fun AccountsCategoriesCard(
    onClickAddCategories: () -> Unit
) {
    OverviewCategoriesCard(
        title = stringResource(R.string.accounts),
        categoryName = stringResource(R.string.add_account_categoty),
        categoryCount = defaultAccountCategories.size,
        modifier = Modifier.fillMaxWidth(0.47f),
        onClickAddCategories = onClickAddCategories,
    )
}

/**
 * The Bills card within the Rally Overview screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillsCategoriesCard(
    onClickAddCategories: () -> Unit
) {
    OverviewCategoriesCard(
        title = stringResource(R.string.bills),
        categoryName = stringResource(R.string.add_bill_categoty),
        categoryCount = defaultBillCategories.size,
        modifier = Modifier.fillMaxWidth(1f),
        onClickAddCategories = onClickAddCategories,
    )
}
