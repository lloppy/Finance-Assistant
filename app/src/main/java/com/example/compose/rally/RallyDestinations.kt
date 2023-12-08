package com.example.compose.rally

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.PieChartOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

/**
 * Contract for information needed on every Rally navigation destination
 */

interface RallyDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Rally app navigation destinations
 */
object Overview : RallyDestination {
    override val icon = Icons.Filled.PieChart
    override val route = "Обзор"
}

object Accounts : RallyDestination {
    override val icon = Icons.Filled.AttachMoney
    override val route = "Счёта"
}

object Bills : RallyDestination {
    override val icon = Icons.Filled.MoneyOff
    override val route = "Расходы"
}

object ChatGPT : RallyDestination {
    override val icon = Icons.Filled.Chat
    override val route = "chatGPT"
}

object Settings : RallyDestination {
    override val icon = Icons.Filled.Settings
    override val route = "Настройки"
}

object Home : RallyDestination {
    override val icon = Icons.Filled.Home
    override val route = "Главная"
}

object AddAccount : RallyDestination {
    override val icon = Icons.Filled.Money
    override val route = "add_account"
    const val accountTypeArg = "account_type"
    val routeWithArgs = "$route/{$accountTypeArg}"
    val arguments = listOf(
        navArgument(accountTypeArg) { type = NavType.StringType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
    )
}

object AddBill : RallyDestination {
    override val icon = Icons.Filled.MoneyOff
    override val route = "add_bill"
    const val billTypeArg = "bill_type"
    val routeWithArgs = "$route/{$billTypeArg}"
    val arguments = listOf(
        navArgument(billTypeArg) { type = NavType.StringType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$billTypeArg}" }
    )
}
object SingleAccount : RallyDestination {
    override val icon = Icons.Filled.Money
    override val route = "single_account"
    const val accountTypeArg = "account_type"
    val routeWithArgs = "$route/{$accountTypeArg}"
    val arguments = listOf(
        navArgument(accountTypeArg) { type = NavType.StringType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
    )
}

object SingleBill : RallyDestination {
    override val icon = Icons.Filled.MoneyOff
    override val route = "single_bill"
    const val billTypeArg = "bill_type"
    val routeWithArgs = "$route/{$billTypeArg}"
    val arguments = listOf(
        navArgument(billTypeArg) { type = NavType.StringType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$billTypeArg}" }
    )
}

// Screens to be displayed in the top RallyTabRow
val rallyTabRowScreens = listOf(Home, Overview, ChatGPT, Settings)
