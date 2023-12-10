package com.example.compose.rally.ui.settings.categories

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.compose.rally.ui.overview.RallyDefaultPadding
import com.example.compose.rally.ui.settings.categories.AccountsCategoriesCard
import com.example.compose.rally.ui.settings.categories.BillsCategoriesCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddCategories(
    onClickAddAccountCategory: () -> Unit = {},
    onClickAddBillCategory: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .semantics { contentDescription = "Îבחמנ" }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AccountsCategoriesCard(
                onClickAddCategories = onClickAddAccountCategory,
            )

            Spacer(modifier = Modifier.width(RallyDefaultPadding))

            BillsCategoriesCard(
                onClickAddCategories = onClickAddBillCategory,
            )
        }
        Spacer(Modifier.height(RallyDefaultPadding))
    }
}