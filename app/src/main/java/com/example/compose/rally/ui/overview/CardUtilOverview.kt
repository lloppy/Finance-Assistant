package com.example.compose.rally.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R


@Composable
internal fun <T> OverViewDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color
) {
    Row(Modifier.fillMaxWidth()) {
        data.drop(1).forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

@Composable
internal fun SeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.see_all))
    }
}