@file:Suppress("DEPRECATION")

package com.example.compose.rally

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.data.util.database.readAccountsFromFile
import com.example.compose.rally.data.util.database.readBillsFromFile
import com.example.compose.rally.data.util.database.readCategoriesFromFile
import com.example.compose.rally.data.util.database.saveAccountsToFile
import com.example.compose.rally.data.util.database.saveBillsToFile
import com.example.compose.rally.data.util.database.saveCategoriesToFile
import com.example.compose.rally.ui.authentication.AuthenticationScreen
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.theme.RallyTheme

class RallyActivity : FragmentActivity() {
    private val permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (!permissions.all { it.value }) {
            Toast.makeText(this, "Пожалуйста, предоставьте все необходимые разрешения", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hasAllPermissions(): Boolean {
        val permissions = arrayOf(
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_SMS
        )

        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_SMS
        )

        permissionsLauncher.launch(permissions)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasAllPermissions()) {
            requestPermissions()
        }

        AccountRepository.setFileAccounts(readAccountsFromFile(this))
        BillRepository.setFileBills(readBillsFromFile(this))
        readCategoriesFromFile(this)
        Log.e("database", "onCreate pick!")

        setContent {
            RallyAuthenticationWrapper()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        saveAccountsToFile(this)
        saveBillsToFile(this)
        saveCategoriesToFile(this)
        Log.e("database", "onDestroy pick!")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RallyAuthenticationWrapper() {
    RallyTheme {
        var isAuthenticated by remember { mutableStateOf(false) }
        val context = LocalContext.current

        if (isAuthenticated
            || readPasswordFromSharedPreferences(context).isNullOrEmpty()
            || readPasswordFromSharedPreferences(context).isNullOrBlank()
        ) {
            RallyApp()
        } else {
            AuthenticationScreen(
                onAuthenticationSuccess = {
                    isAuthenticated = true
                },
                onAuthenticationFailed = {
                    Toast.makeText(
                        context as FragmentActivity,
                        "Ошибка аутентификации",
                        Toast.LENGTH_SHORT
                    ).show()
                    isAuthenticated = false
                }
            )
            if (!isAuthenticated) {
                PasswordScreen(onAuthenticationSuccess = {
                    isAuthenticated = true
                })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RallyApp() {
    RallyTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Home

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            RallyNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PasswordScreen(onAuthenticationSuccess: () -> Unit) {
    var password by remember { mutableStateOf("") }

    PasswordAuthentication(
        password = password,
        onPasswordChange = { password = it },
        onAuthenticationSuccess = onAuthenticationSuccess,
        onAuthenticationFailed = {
            // Handle authentication failure
        }
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordAuthentication(
    password: String,
    onPasswordChange: (String) -> Unit,
    onAuthenticationSuccess: () -> Unit,
    onAuthenticationFailed: () -> Unit
) {
    var showError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 128.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = {
                onPasswordChange(it)
                showError = false
            },

            label = { Text(text = "Введите пароль", color = Color.White) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (password == readPasswordFromSharedPreferences(context) || password.isNullOrEmpty() || password.isNullOrBlank()) {
                        onAuthenticationSuccess()
                    } else {
                        showError = true
                        onAuthenticationFailed()
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                },
            ),
            isError = showError
        )
    }
}

private fun readPasswordFromSharedPreferences(context: Context): String? {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPreferences.getString("password", null)
}
