package com.example.compose.rally.ui.chat

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.rally.data.analyze.getAccountStatisticReport
import com.example.compose.rally.data.analyze.getBillMCCReport
import com.example.compose.rally.data.analyze.getBillSpendStatisticReport
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<Message>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(text: String, isUser: Boolean = true, context: Context) {
        messages.add(Message(text, "user"))
        if (isUser) {
            viewModelScope.launch {
                try {
                    val response =
                        ApiService.openAIApi.generateResponse(OpenAIRequestBody(messages = messages))
                    messages.add(response.choices.first().message)
                } catch (e: Exception) {
                    Log.e("gpt", "Exception is ${e.message}")

                    messages.add(Message(getBillSpendStatisticReport(), "user"))
                    messages.add(Message(getAccountStatisticReport(), "user"))
                    messages.add(Message(getBillMCCReport(), "user"))
                    toastErrorMessage(context, e)
                    //errorMessage(messages, context, e)
                    //errorMessage(messages, context, e)
                }
            }
        }
    }
}

private fun errorMessage(messages: SnapshotStateList<Message>, context: Context, e: Exception) {
    var response =
        "Сервис openAI недоступен.\nПроверьте VPN подключения и доступные активации!\nКупите подходящий тариф можно на сайте openAI."
    messages.add(Message(response, "user"))
    Log.e("gpt", "Exception 429 is ${e.message}")

    Toast.makeText(
        context,
        response,
        Toast.LENGTH_SHORT
    ).show()
}

private fun toastErrorMessage(context: Context, e: Exception) {
    var response =
        "Сервис openAI недоступен.\nПроверьте VPN подключения и доступные активации!\nКупите подходящий тариф можно на сайте openAI."
    Log.e("gpt", "Exception 429 is ${e.message}")

    Toast.makeText(
        context,
        response,
        Toast.LENGTH_SHORT
    ).show()
}

data class Message(val content: String, val role: String) {
    val isUser: Boolean
        get() = role == "user"
}