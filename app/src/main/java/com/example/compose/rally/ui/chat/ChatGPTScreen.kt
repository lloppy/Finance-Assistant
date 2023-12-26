package com.example.compose.rally.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.rally.R

/**
 * The ChatGPT screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatGPTScreen(viewModel: ChatViewModel) {
    var context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Image(
            painter = painterResource(id = R.drawable.enderman),
            contentDescription = "enderman",
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(start = 8.dp, bottom = 32.dp)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = true
        ) {
            items(viewModel.messages.reversed()) { message ->
                if (message.isUser) {
                    MessageBubble(message.content, Alignment.End, Color.Transparent, Color.Black)
                } else {
                    MessageBubble(message.content, Alignment.Start, colorResource(id = R.color.pinkMaterial), Color.White)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            var inputText by remember { mutableStateOf("Сделай анализ моих финансов") }
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Введите запрос") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    viewModel.sendMessage(inputText, true, context)
                    inputText = ""
                })
            )

            IconButton(
                onClick = {
                    viewModel.sendMessage(inputText, true, context)
                    inputText = ""
                },
                modifier = Modifier.padding(
                    start = 5.dp,
                    top = 6.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Отправить запрос",
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun MessageBubble(
    text: String,
    alignment: Alignment.Horizontal,
    color: Color,
    textColor: Color
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentWidth(alignment),
    ) {
        Text(
            text = text,
            modifier = Modifier.background(color).padding(16.dp),
            fontSize = 16.sp,
            color = textColor
        )
    }
}