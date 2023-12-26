package com.example.compose.rally.ui.bills

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.CalendarView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.compose.rally.R
import com.example.compose.rally.data.bill.Bill
import com.example.compose.rally.data.bill.BillRepository
import com.example.compose.rally.data.category.defaultBillCategories
import com.example.compose.rally.ui.accounts.CategoryDropdown
import com.example.compose.rally.ui.accounts.RepeatDataDropdown
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBillScreen(
    billType: String? = BillRepository.bills.first().name,
    onSaveClick: (Bill) -> Unit = {},
//    onBackClick: () -> Unit = {},
) {

    val bill = remember(billType) { BillRepository.getBill(billType) }
    Log.e("route", "bill name is ${bill.name}")

    var selectedCategory by remember { mutableStateOf(defaultBillCategories.first()) }
    var billName by remember { mutableStateOf(TextFieldValue()) }
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var balance by remember { mutableStateOf(TextFieldValue()) }

    var repeatRuleOptions =
        listOf("Только один день", "Каждый день", "Каждую неделю", "Каждый месяц")
    var selectedRepeatRule by remember { mutableStateOf(repeatRuleOptions[0]) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "*/*";
    intent.addCategory(Intent.CATEGORY_OPENABLE);

    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageUri ->
        if (imageUri != null) {
            selectedImageUri = imageUri
            Log.e("picImg", "imageUri change to: $imageUri")
        }
    }

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
            label = { Text(stringResource(id = R.string.balance_bill)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        CategoryDropdown(
            categories = defaultBillCategories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(16.dp))
        showDatePicker { selectedDate = it }

        RepeatDataDropdown(
            repeatRuleOptions = repeatRuleOptions,
            selectedRepeatRule = selectedRepeatRule,
            onRepeatRuleSelected = { selectedRepeatRule = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                pickPictureLauncher.launch("image/*")
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.attach_photo))
        }

        AsyncImage(
            model = selectedImageUri,
            placeholder = painterResource(R.drawable.enderhead),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (billName.text.isNotEmpty() && balance.text.isNotEmpty()) {
                    onSaveClick(
                        Bill(
                            name = billName.text,
                            date = selectedDate,
                            timesRepeat = repeatRuleOptions.indexOf(selectedRepeatRule),
                            billPhoto = selectedImageUri,
                            category = selectedCategory,
                            mcc = null,
                            amount = balance.text.replace(" ", "").toFloat()
                        )
                    )
                }
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

    AndroidView({
        CalendarView(android.view.ContextThemeWrapper(it, R.style.CustomCalendar)).apply {
            dateTextAppearance = R.style.CustomDate
            weekDayTextAppearance = R.style.CustomWeek
        }
    },
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
