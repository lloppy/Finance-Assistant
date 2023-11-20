package com.example.compose.rally.ui.accounts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.Account
import com.example.compose.rally.data.UserRepository
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * Composable for the screen where a user can manually add a new account.
 */
@Composable
@Preview
fun AddAccountScreenPreview() {
    RallyTheme {
        Surface {
            AddAccountScreen()
        }
    }
}

@Composable
fun AddAccountScreen(
    accountType: String? = UserRepository.accounts.first().name,
    onSaveClick: (Account) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val account = remember(accountType) { UserRepository.getAccount(accountType) }
    Log.e("route", "account name is ${account.name}" )

    var accountName by remember { mutableStateOf(TextFieldValue()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var balance by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.add_account),
                    style = MaterialTheme.typography.h6
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onBackClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = { Text(stringResource(id = R.string.account_name)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text(stringResource(id = R.string.card_number)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = balance,
            onValueChange = { balance = it },
            label = { Text(stringResource(id = R.string.balance)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )


        // Save button
        Button(
            onClick = {
                onSaveClick(
                    Account(
                        name = accountName.text,
                        cardNumber = cardNumber.text.toInt(),
                        balance = balance.text.toFloat()
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {

            Text(text = stringResource(id = R.string.save))
        }
    }
}
