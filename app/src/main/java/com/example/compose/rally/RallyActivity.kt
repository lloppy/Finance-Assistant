package com.example.compose.rally

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
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
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.rally.ui.authentication.AuthenticationScreen
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.theme.RallyTheme

class RallyActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyAuthenticationWrapper()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RallyAuthenticationWrapper() {
    RallyTheme {
        var isAuthenticated by remember { mutableStateOf(false) }
        val context = LocalContext.current

        if (isAuthenticated) {
            RallyApp()
        } else {
            AuthenticationScreen(
                onAuthenticationSuccess = {
                    isAuthenticated = true
                },
                onAuthenticationFailed = {
                    Toast.makeText(
                        context as FragmentActivity,
                        "Аутентификация не удалась",
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
            rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview

        // UserRepository.readFile(context)

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

            label = { Text(text = "Введите свой пароль", color = Color.White) },
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
                    if (password == "1234") {
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