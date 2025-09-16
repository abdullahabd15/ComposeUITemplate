package com.absolution.composeuitemplate.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.absolution.composeuitemplate.R
import com.absolution.composeuitemplate.ui.viewmodel.Chat
import com.absolution.composeuitemplate.ui.viewmodel.ChatUiState
import com.absolution.composeuitemplate.ui.viewmodel.ChatViewModel
import com.absolution.composeuitemplate.ui.viewmodel.Message
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ChatPage(
    chat: Chat?,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setChat(chat)
    }

    ChatUi(
        uiState = uiState,
        onBackPressed = onBackPressed,
        onSendMessage = viewModel::sendMessage,
        onTypingMessageChange = viewModel::onTypingMessageChange,
        modifier = modifier.fillMaxSize().background(color = Color.White)
    )
}

@Composable
private fun ChatUi(
    uiState: ChatUiState,
    onBackPressed: () -> Unit,
    onSendMessage: () -> Unit,
    onTypingMessageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val softKeyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null, tint = Color.Blue)
            }
            Text(
                uiState.chat?.userName.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp).size(48.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.chat?.messages?.size ?: 0) {
                val message = uiState.chat?.messages?.get(it)
                val isMine = message?.isMine ?: false
                if (isMine) {
                    ItemChatRight(
                        senderName = uiState.name,
                        message = message,
                        needToDisplayName = message.needToDisplayName
                    )
                } else {
                    ItemChatLeft(
                        senderName = uiState.chat?.userName,
                        message = message,
                        needToDisplayName = message?.needToDisplayName ?: true
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp).padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(36.dp)
                )
            }
            OutlinedTextField(
                value = uiState.typingMessage,
                onValueChange = onTypingMessageChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type a message...")
                },
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Blue.copy(alpha = 0.08f),
                    unfocusedContainerColor = Color.Blue.copy(alpha = 0.08f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onSendMessage()
                            softKeyboardController?.hide()
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color.Blue, shape = CircleShape)
                                .padding(8.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.Send,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ItemChatLeft(
    senderName: String?,
    message: Message?,
    modifier: Modifier = Modifier,
    needToDisplayName: Boolean = true,
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp).padding(start = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .background(
                    color = Color.Blue.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(
                        topEnd = 12.dp,
                        topStart = 12.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 12.dp
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (needToDisplayName && senderName != null) {
                Text(
                    senderName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                message?.text.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ItemChatRight(
    senderName: String?,
    message: Message?,
    modifier: Modifier = Modifier,
    needToDisplayName: Boolean = true,
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp).padding(end = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(
                        topEnd = 12.dp,
                        topStart = 12.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (needToDisplayName && senderName != null) {
                Text(
                    senderName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Text(
                message?.text.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
@Preview
private fun ChatPreview() {
    ChatUi(
        uiState = ChatUiState(),
        onBackPressed = {},
        onSendMessage = {},
        onTypingMessageChange = {},
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    )
}