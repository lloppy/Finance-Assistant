package com.example.compose.rally.ui.settings

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.google.android.material.internal.ViewUtils.hideKeyboard

@Composable
fun SettingsScreen() {
    var spendingGoal by remember { mutableStateOf("Не откладывать") }
    var apiChatKey by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    var incomeCategoryName by remember { mutableStateOf("") }
    var expenseCategoryName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Spending Goal
        SpendingGoalSetting(spendingGoal, onSpendingGoalSelected = { selectedGoal ->
            spendingGoal = selectedGoal
        })

        // API Key for Chat
        ApiKeySetting(apiChatKey, onApiKeyChanged = { apiChatKey = it })

        // Password Setting
        PasswordSetting(password, showPassword, onPasswordChanged = { password = it },
            onTogglePasswordVisibility = { showPassword = it })

        // Income Category
        AddCategorySetting(
            incomeCategoryName, onCategoryAdded = { incomeCategoryName = it },
            icon = Icons.Default.Money
        )

        // Expense Category
        AddCategorySetting(
            expenseCategoryName, onCategoryAdded = { expenseCategoryName = it },
            icon = Icons.Default.Category
        )
    }
}

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
                        "Не откладывать"
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

@Composable
fun ApiKeySetting(apiKey: String, onApiKeyChanged: (String) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(top = 16.dp)
    ) {
        Text(text = stringResource(R.string.api_key_for_chat))

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = apiKey,
            onValueChange = { onApiKeyChanged(it) },
            label = { Text(text = "Enter API Key") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    hideKeyboard(context)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun PasswordSetting(
    password: String,
    showPassword: Boolean,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: (Boolean) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(stringResource(R.string.password_setting))

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text(stringResource(R.string.enter_password)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    hideKeyboard(context)
                }
            ),
            trailingIcon = {
                IconButton(onClick = { onTogglePasswordVisibility(!showPassword) }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(id = R.string.toggle_password_visibility)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AddCategorySetting(
    categoryName: String,
    onCategoryAdded: (String) -> Unit,
    icon: ImageVector,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(stringResource(R.string.add_category), style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = categoryName,
            onValueChange = { /* Do not update categoryName directly */ },
            label = { Text(stringResource(R.string.enter_category_name)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    hideKeyboard(context)
                }
            ),
            trailingIcon = {
                IconButton(onClick = {
                    onCategoryAdded(categoryName)
                }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(id = R.string.add_category)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun hideKeyboard(context: Context) {
    val view = (context as? Activity)?.currentFocus
    if (view != null) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}