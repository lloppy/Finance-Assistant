@file:Suppress("DEPRECATION")

package com.example.compose.rally.ui.settings

import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.glance.LocalContext
import com.example.compose.rally.R
import com.example.compose.rally.ui.overview.RallyDefaultPadding
import com.example.compose.rally.ui.theme.Ender

@Composable
fun SpendingGoalSetting(
    context: Context,
    selectedGoal: String,
    onSpendingGoalSelected: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .padding(8.dp)
            .background(colorResource(id = R.color.boxColor))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.current_category, selectedGoal)
            )
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
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Ender,
                                    unselectedColor = Color.Gray
                                ),
                                selected = selectedOption == index,
                                onClick = {
                                    selectedOption = index
                                    onSpendingGoalSelected(option)
                                    showDialog = false

                                    saveGoalToSharedPreferences(option, context)
                                },
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


private fun saveGoalToSharedPreferences(goal: String, context: Context) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putString("current_goal", goal)
    editor.apply()
}