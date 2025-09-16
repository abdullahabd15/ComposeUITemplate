package com.absolution.composeuitemplate.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    fun setChat(chat: Chat?) {
        _uiState.update {
            it.copy(
                chat = chat?.copy(
                messages = chat.messages.mapIndexed { index, x ->
                    val needToDisplayName =
                        index == 0 || chat.messages[index - 1].senderName != x.senderName
                    x.copy(
                        needToDisplayName = needToDisplayName
                    )
                }
            ))
        }
    }

    fun onTypingMessageChange(typingMessage: String) {
        _uiState.update {
            it.copy(typingMessage = typingMessage)
        }
    }

    fun sendMessage() {
        _uiState.update {
            it.copy(typingMessage = "")
        }
    }
}

data class ChatUiState(
    val typingMessage: String = "",
    val name: String = "John Doe",
    val chat: Chat? = null
)