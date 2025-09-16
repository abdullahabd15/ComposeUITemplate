package com.absolution.composeuitemplate.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier
) {
    SettingsUi(
        onProfilePictureClick = {},
        onSettingClick = {},
        onLogoutClick = {},
        modifier = modifier.fillMaxSize().background(color = Color.White)
    )
}

@Composable
private fun SettingsUi(
    onProfilePictureClick: () -> Unit,
    onSettingClick: (Settings) -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.padding(horizontal = 16.dp).verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(36.dp))
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Box(modifier = Modifier) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.Blue.copy(alpha = 0.3f),
                modifier = Modifier.size(132.dp),
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .size(32.dp)
                    .background(color = Color.Blue, shape = CircleShape)
                    .align(alignment = Alignment.BottomEnd)
            ) {
                IconButton(onClick = onProfilePictureClick) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp).align(alignment = Alignment.Center)
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            "John Doe",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text("@john_doe")
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Settings.entries.forEach { setting ->
                SettingsItem(
                    title = setting.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable(enabled = true) {
                            onSettingClick(setting)
                        }
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onLogoutClick) {
            Text(
                "Log Out",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SettingsItem(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.AutoMirrored.Default.ArrowForwardIos,
                contentDescription = null
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

enum class Settings(val title: String) {
    SAVED_MESSAGES("Saved Messages"),
    RECENT_CALLS("Recent Calls"),
    DEVICES("Devices"),
    NOTIFICATIONS("Notifications"),
    APPEARANCE("Appearance"),
    LANGUAGE("Language"),
    PRIVACY_SECURITY("Privacy & Security"),
    STORAGE("Storage"),
}

@Composable
@Preview
private fun SettingsPreview() {
    SettingsUi(
        onProfilePictureClick = {},
        onSettingClick = {},
        onLogoutClick = {},
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    )
}