package com.example.compose.rally.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R

@Composable
fun SpendingGoalSetting(
    selectedGoal: String,
    onSpendingGoalSelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showDialog = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.current_category, selectedGoal))
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.select_category)) },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { showDialog = false }) {
                        Text(text = stringResource(R.string.close))
                    }
                }
            },
            text = {
                Column {
                    val options = listOf(
                        "Откладывать 50%",
                        "Откладывать 20%",
                        "Откладывать 10%",
                        "Не определена"
                    )

                    options.forEachIndexed { index, option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOption == index,
                                onClick = {
                                    selectedOption = index
                                    onSpendingGoalSelected(option)
                                    showDialog = false
                                },
                            )
                            Text(
                                text = option,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
