package com.absolution.composeuitemplate.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.absolution.composeuitemplate.R
import com.absolution.composeuitemplate.ui.components.SearchBar
import com.absolution.composeuitemplate.ui.viewmodel.Chat
import com.absolution.composeuitemplate.ui.viewmodel.ChatListUiState
import com.absolution.composeuitemplate.ui.viewmodel.ChatListViewModel
import com.absolution.composeuitemplate.ui.viewmodel.generateRealisticChats
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatListPage(
    onChatClicked: (Chat) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChatListUi(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClearSearch = viewModel::onClearSearch,
        onNewChatClicked = {},
        onChatClicked = onChatClicked,
        modifier = modifier.fillMaxSize().background(color = Color.White)
    )
}

@Composable
private fun ChatListUi(
    uiState: ChatListUiState,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNewChatClicked: () -> Unit,
    onChatClicked: (Chat) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ChatListHeader(
            searchQuery = uiState.searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onClearSearch = onClearSearch,
            onNewChatClicked = onNewChatClicked,
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn {
            items(uiState.chats.size) { index ->
                val chat = uiState.chats[index]
                ItemChat(
                    chat = chat,
                    modifier = Modifier.fillMaxWidth().padding(16.dp).clickable {
                        onChatClicked(chat)
                    }
                )
            }
        }
    }
}

@Composable
private fun ChatListHeader(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNewChatClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {}) {}
            Text("Chats", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            IconButton(
                onClick = onNewChatClicked
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.ChatBubbleOutline,
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.size(24.dp)
                    )
                    Icon(
                        Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.size(12.dp).offset(y = (-3).dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        SearchBar(
            placeholder = "Search",
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            onClear = onClearSearch,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun ItemChat(
    chat: Chat,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                chat.userName.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                chat.lastMessage?.text.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                chat.lastMessage?.time.toString(),
                style = MaterialTheme.typography.bodyMedium,
            )
            if (chat.isUnread) {
                Box(
                    modifier = Modifier
                        .background(color = Color.Blue, shape = CircleShape)
                        .size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        chat.unreadCount.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ChatListPreview() {
    ChatListUi(
        uiState = ChatListUiState(
            chats = generateRealisticChats()
        ),
        onSearchQueryChange = {},
        onClearSearch = {},
        onNewChatClicked = {},
        onChatClicked = {},
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    )
}