package com.example.compose.rally.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R

class SettingsViewModel {
    var spendingGoal by mutableStateOf("Не определена")
    var apiChatKey by mutableStateOf("")
    var password by mutableStateOf("")
    var showPassword by mutableStateOf(false)
}

@Composable
fun SettingsScreen() {
    val viewModel = remember { SettingsViewModel() }
    SettingsScreenContent(viewModel, LocalContext.current)
}

@Composable
fun SettingsScreenContent(
    viewModel: SettingsViewModel,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SpendingGoalSetting(context, viewModel.spendingGoal, onSpendingGoalSelected = { selectedGoal ->
            viewModel.spendingGoal = selectedGoal
        })
        ApiKeySetting(viewModel.apiChatKey, onApiKeyChanged = { viewModel.apiChatKey = it })

        PasswordSetting(
            viewModel.password,
            viewModel.showPassword,
            onPasswordChanged = { viewModel.password = it },
            onTogglePasswordVisibility = { viewModel.showPassword = it }
        )

        AddCategorySetting(stringResource(R.string.add_account_categoty))
        AddCategorySetting(stringResource(R.string.add_bill_categoty))
    }
}



