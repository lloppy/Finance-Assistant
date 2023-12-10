package com.example.compose.rally.ui.settings.categories

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.rally.R
import com.example.compose.rally.data.category.defaultAccountCategories
import com.example.compose.rally.ui.theme.RallyTheme
import java.util.Locale

class AddAccountCategoryScreen : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AddCategoryScreen(onCategoryAdded = { finish() })
                }
            }
        }
    }
}


@Composable
private fun AddCategoryScreen(onCategoryAdded: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, bottom = 160.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Добавить новую категорию", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        var categoryName by remember { mutableStateOf("") }

        TextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text(text = "Введите название категории") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!defaultAccountCategories.contains(categoryName) && categoryName!="") {
                defaultAccountCategories += categoryName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                onCategoryAdded()
            }
        }) {
            Text(
                text = stringResource(id = R.string.add_category),
                // color = Color.White
            )
        }
    }
}
