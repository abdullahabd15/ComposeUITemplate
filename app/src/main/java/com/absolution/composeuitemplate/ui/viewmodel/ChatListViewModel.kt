package com.absolution.composeuitemplate.ui.viewmodel

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import kotlin.random.Random
import kotlin.time.ExperimentalTime

class ChatListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    init {
        getChats()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    fun onClearSearch() {
        _uiState.update {
            it.copy(searchQuery = "")
        }
    }

    fun getChats() {
        _uiState.update {
            it.copy(chats = generateRealisticChats())
        }
    }
}

data class ChatListUiState(
    val searchQuery: String = "",
    val chats: List<Chat> = emptyList(),
)

@Parcelize
data class Chat(
    val sessionId: Int? = 0,
    val userName: String? = null,
    val messages: List<Message> = emptyList(),
) : Parcelable {
    @IgnoredOnParcel
    val lastMessage: Message? = messages.lastOrNull()

    @IgnoredOnParcel
    val isUnread: Boolean = messages.any { !it.isRead }

    @IgnoredOnParcel
    val unreadCount: Int = messages.filter { !it.isRead }.size
}

@Parcelize
data class Message(
    val id: Int? = null,
    val text: String? = null,
    val isRead: Boolean = false,
    val time: String? = null,
    val senderName: String? = null,
    val needToDisplayName: Boolean = false,
    val isMine: Boolean = false
) : Parcelable

private val sampleUserNames = listOf(
    "Alice", "Bob", "Charlie", "Diana", "Eve",
    "Frank", "Grace", "Hank", "Ivy", "Jack",
    "Karen", "Leo", "Mona", "Nick", "Olivia",
    "Paul", "Quincy", "Rachel", "Sam", "Tina"
)

private val sampleMessages = listOf(
    "Hey, how are you?",
    "Did you check the file I sent?",
    "Let’s meet tomorrow at 5.",
    "Sure, sounds good!",
    "I’ll call you later.",
    "Don’t forget about the meeting.",
    "That’s awesome news!",
    "What are you doing now?",
    "Okay, see you soon.",
    "Thanks for your help!",
    "Can you send me the link?",
    "I’ll be there in 10 minutes."
)

@OptIn(ExperimentalTime::class)
fun generateRealisticChats(): List<Chat> {
    return (1..20).map { chatIndex ->
        val userName = sampleUserNames.random()
        val messageCount = Random.nextInt(3, 30)
        val messages = (1..messageCount).map { messageIndex ->
            val dateTime = Calendar.getInstance()
            val hour = dateTime.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
            val minute = dateTime.get(Calendar.MINUTE).toString().padStart(2, '0')
            val formattedTime = "$hour:$minute"
            val sendersName = arrayOf(userName, "John Doe")
            val senderName = sendersName.random()
            Message(
                id = (chatIndex * 100) + messageIndex,
                text = sampleMessages.random(),
                isRead = Random.nextBoolean(),
                time = formattedTime,
                senderName = senderName,
                isMine = senderName == "John Doe"
            )
        }

        Chat(
            sessionId = chatIndex,
            userName = userName,
            messages = messages
        )
    }
}