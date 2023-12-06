package com.example.compose.rally.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R

class SettingsViewModel {
    var spendingGoal by mutableStateOf("Не откладывать")
    var apiChatKey by mutableStateOf("")
    var password by mutableStateOf("")
    var showPassword by mutableStateOf(false)
}

@Composable
fun SettingsScreen() {
    val viewModel = remember { SettingsViewModel() }
    SettingsScreenContent(viewModel)
}

@Composable
fun SettingsScreenContent(viewModel: SettingsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SpendingGoalSetting(viewModel.spendingGoal, onSpendingGoalSelected = { selectedGoal ->
            viewModel.spendingGoal = selectedGoal
        })

        // API Key for Chat
        ApiKeySetting(viewModel.apiChatKey, onApiKeyChanged = { viewModel.apiChatKey = it })

        // Password Setting
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



