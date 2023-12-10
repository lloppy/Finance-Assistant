package com.example.compose.rally.ui.settings

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.compose.rally.ui.home.OverviewHome
import com.example.compose.rally.ui.overview.RallyDefaultPadding

class SettingsViewModel {
    var spendingGoal by mutableStateOf("Не определена")
    var apiChatKey by mutableStateOf("")
    var password by mutableStateOf("")
    var showPassword by mutableStateOf(false)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    onClickAddAccountCategory: () -> Unit = {},
    onClickAddBillCategory: () -> Unit = {},
) {
    val viewModel = remember { SettingsViewModel() }
    SettingsScreenContent(
        onClickAddAccountCategory,
        onClickAddBillCategory,
        viewModel,
        LocalContext.current
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreenContent(
    onClickAddAccountCategory: () -> Unit = {},
    onClickAddBillCategory: () -> Unit = {},
    viewModel: SettingsViewModel,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        SpendingGoalSetting(
            context,
            viewModel.spendingGoal,
            onSpendingGoalSelected = { selectedGoal ->
                viewModel.spendingGoal = selectedGoal
            })

        ApiKeySetting(viewModel.apiChatKey, onApiKeyChanged = { viewModel.apiChatKey = it })

        PasswordSetting(
            viewModel.password,
            viewModel.showPassword,
            onPasswordChanged = { viewModel.password = it },
            onTogglePasswordVisibility = { viewModel.showPassword = it }
        )

        Spacer(Modifier.height(RallyDefaultPadding))
        Spacer(Modifier.height(RallyDefaultPadding))

        AddCategories(
            onClickAddAccountCategory = onClickAddAccountCategory,
            onClickAddBillCategory = onClickAddBillCategory,
        )
    }
}
