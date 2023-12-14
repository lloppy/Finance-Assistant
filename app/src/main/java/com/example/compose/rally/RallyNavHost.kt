package com.example.compose.rally

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.AddAccountScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.AddBillScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.bills.SingleBillScreen
import com.example.compose.rally.ui.chat.ChatGPTScreen
import com.example.compose.rally.ui.chat.ChatViewModel
import com.example.compose.rally.ui.home.HomeScreen
import com.example.compose.rally.ui.overview.OverviewScreen
import com.example.compose.rally.ui.settings.SettingsScreen
import com.example.compose.rally.ui.settings.categories.AddAccountCategoryScreen
import com.example.compose.rally.ui.settings.categories.AddBillCategoryScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(
                onAddBillClick = {
                    navController.navigateToAddBill(AddBill.route)
                },
                onClickAnalyze = {
                    navController.navigateSingleTopTo(ChatGPT.route)
                },
                onClickSeeAllAccounts = {
                    navController.navigateSingleTopTo(Accounts.route)
                },
                onClickSeeAllBills = {
                    navController.navigateSingleTopTo(Bills.route)
                }
            )
        }
        composable(route = Overview.route) {
            OverviewScreen(
                onClickSeeAllAccounts = {
                    navController.navigateSingleTopTo(Accounts.route)
                },
                onClickSeeAllBills = {
                    navController.navigateSingleTopTo(Bills.route)
                },
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                },
                onBillClick = { billType ->
                    navController.navigateToSingleBill(billType)
                }
            )
        }
        composable(route = Accounts.route) {
            AccountsScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                    Log.e("route", "accountType is $accountType")
                },
                onAddAccountClick = {
                    navController.navigateToAddAccount(AddAccount.route)
                    Log.e("route", "AddAccount route is ${AddAccount.route}")
                }
            )
        }
        composable(route = Bills.route) {
            BillsScreen(
                onBillClick = { billType ->
                    navController.navigateToSingleBill(billType)
                    Log.e("route", "billType is $billType")
                },
                onAddBillClick = {
                    navController.navigateToAddBill(AddBill.route)
                    Log.e("route", "AddBill route is ${AddBill.route}")
                }
            )
        }
        composable(route = Settings.route) {
            var context = LocalContext.current
            SettingsScreen(
                onClickAddAccountCategory = {
                    val intent = Intent(context, AddAccountCategoryScreen::class.java)
                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity)
                    context.startActivity(intent, options.toBundle())
                },
                onClickAddBillCategory = {
                    val intent = Intent(context, AddBillCategoryScreen::class.java)
                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity)
                    context.startActivity(intent, options.toBundle())
                }
            )
        }
        composable(route = ChatGPT.route) {
            val chatViewModel = viewModel<ChatViewModel>()
            ChatGPTScreen(chatViewModel)
        }
        composable(
            route = SingleAccount.routeWithArgs,
            arguments = SingleAccount.arguments,
            deepLinks = SingleAccount.deepLinks
        ) { navBackStackEntry ->
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
            SingleAccountScreen(
                accountType = accountType,
                onDeleteAccountClick = {
                    Log.e("delete", "Было: " + AccountRepository.getAllAccounts().size.toString())
                    AccountRepository.removeAccount(it)
                    Log.e("delete", "Стало: " + AccountRepository.getAllAccounts().size.toString())
                    navController.navigateSingleTopTo(Accounts.route)
                }, navController = navController
            )

        }
        composable(
            route = AddAccount.routeWithArgs,
            arguments = AddAccount.arguments,
            deepLinks = AddAccount.deepLinks
        ) {// navBackStackEntry ->
//            val accountType =
//                navBackStackEntry.arguments?.getString(AddAccount.accountTypeArg)
            AddAccountScreen(
                onSaveClick = { account ->
                    AccountRepository.addAccount(account)
                    navController.navigateSingleTopTo(Accounts.route)
                }, navController = navController
            )
        }
        composable(
            route = SingleBill.routeWithArgs,
            arguments = SingleBill.arguments,
            deepLinks = SingleBill.deepLinks
        ) { navBackStackEntry ->
            val billType =
                navBackStackEntry.arguments?.getString(SingleBill.billTypeArg)
            SingleBillScreen(
                billType = billType,
                onDeleteBillClick = {
                    Log.e("delete", "Было: " + BillRepository.bills.size.toString())
                    BillRepository.removeBill(it)
                    Log.e("delete", "Стало: " + BillRepository.bills.size.toString())
                    navController.navigateSingleTopTo(Bills.route)
                }, navController = navController
            )
        }
        composable(
            route = AddBill.routeWithArgs,
            arguments = AddBill.arguments,
            deepLinks = AddBill.deepLinks
        ) { navBackStackEntry ->
            val billType =
                navBackStackEntry.arguments?.getString(AddBill.billTypeArg)
            val context = LocalContext.current
            AddBillScreen(
                billType,
                onSaveClick = { bill ->
                    BillRepository.addBill(bill, context)
                    navController.navigateSingleTopTo(Bills.route)
                },
                navController = navController
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

private fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}

private fun NavHostController.navigateToAddAccount(accountType: String) {
    this.navigateSingleTopTo("${AddAccount.route}/$accountType")
    Log.e("route", "navigateToAddAccount is ${AddAccount.route}/$accountType ")
}

private fun NavHostController.navigateToSingleBill(billType: String) {
    this.navigateSingleTopTo("${SingleBill.route}/$billType")
}

private fun NavHostController.navigateToAddBill(billType: String) {
    this.navigateSingleTopTo("${AddBill.route}/$billType")
    Log.e("route", "navigateToAddBill is ${AddBill.route}/$billType ")
}
