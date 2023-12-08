package com.example.compose.rally.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
            label = { Text(text = "Введите API Key") },
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

