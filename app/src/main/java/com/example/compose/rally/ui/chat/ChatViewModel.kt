package com.example.compose.rally.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.account.AccountRepository.Companion.accounts
import com.example.compose.rally.data.bill.BillRepository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<Message>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(text: String, isUser: Boolean = true) {
        messages.add(
            Message(text, "user")
        )
        if (isUser) {
            viewModelScope.launch {
                val response =
                    ApiService.openAIApi.generateResponse(OpenAIRequestBody(messages = messages))
                messages.add(response.choices.first().message)
            }
        }
    }
}

data class Message(val content: String, val role: String) {
    val isUser: Boolean
        get() = role == "user"
}