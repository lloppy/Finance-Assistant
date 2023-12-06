package com.example.compose.rally.ui.settings

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
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.util.hideKeyboard

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

