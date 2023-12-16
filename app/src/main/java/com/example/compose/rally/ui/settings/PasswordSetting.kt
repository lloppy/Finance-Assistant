@file:Suppress("DEPRECATION")

package com.example.compose.rally.ui.settings

import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.util.hideKeyboard
import com.example.compose.rally.ui.theme.Ender

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
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text(stringResource(R.string.enter_password)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    hideKeyboard(context)
                    savePasswordToSharedPreferences(password, context)
                    // password
                }
            ),
            trailingIcon = {
                IconButton(onClick = { onTogglePasswordVisibility(!showPassword) }) {
                    Icon(
                        imageVector = if (showPassword) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = stringResource(id = R.string.toggle_password_visibility),
                        tint = Ender
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun savePasswordToSharedPreferences(password: String, context: Context) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putString("password", password)

    Toast.makeText(context, "Пароль $password сохранен", Toast.LENGTH_SHORT).show()
    editor.apply()
}