package com.example.compose.rally.ui.bills

import android.os.Build
import android.util.Log
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.rally.R
import com.example.compose.rally.data.Bill
import com.example.compose.rally.data.UserRepository
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBillScreen(
    billType: String? = UserRepository.bills.first().name,
    onSaveClick: (Bill) -> Unit = {},
//    onBackClick: () -> Unit = {},
) {
    val bill = remember(billType) { UserRepository.getBill(billType) }
    Log.e("route", "bill name is ${bill.name}")

    var selectedCategory by remember { mutableStateOf(UserRepository.billCategories.first()) }
    var billName by remember { mutableStateOf(TextFieldValue()) }
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var balance by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ) {
//        TopAppBar(
//            title = {
//                Text(
//                    text = stringResource(id = R.string.add_bill),
//                    style = MaterialTheme.typography.h6
//                )
//            },
//            navigationIcon = {
//                IconButton(onClick = {
//                    onBackClick()
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = null
//                    )
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = billName,
            onValueChange = { billName = it },
            label = { Text(stringResource(id = R.string.bill_name)) },
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

        Spacer(modifier = Modifier.height(16.dp))

        CategoryDropdown(
            categories = UserRepository.billCategories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        showDatePicker { selectedDate = it }

        Button(
            onClick = {
                onSaveClick(
                    Bill(
                        name = billName.text,
                        date = selectedDate,
                        category = selectedCategory,
                        amount = balance.text.toFloat()
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.save_bill))
        }
    }
}


@Composable
fun showDatePicker(onDateSelected: (LocalDateTime) -> Unit) {
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }

    AndroidView(
        { CalendarView(it) },
        modifier = Modifier.wrapContentWidth(),
        update = { views ->
            views.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                val selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                selectedDate = selectedDateTime
                onDateSelected(selectedDateTime)

                Log.e("datePick", "$calendarView, $year, $month, $dayOfMonth")
            }
        }
    )
}

@Composable
fun CategoryDropdown(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.boxColor))
            .padding(14.dp)

    ) {
        Text(
            text = "Категория: $selectedCategory",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                ) {
                    Text(text = category)
                }
            }
        }
    }
}
