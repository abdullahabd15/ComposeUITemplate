package com.absolution.composeuitemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.absolution.composeuitemplate.ui.pages.ChatListPage
import com.absolution.composeuitemplate.ui.pages.ChatPage
import com.absolution.composeuitemplate.ui.pages.FeedbackPage
import com.absolution.composeuitemplate.ui.theme.ComposeUITemplateTheme
import com.absolution.composeuitemplate.ui.viewmodel.Chat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUITemplateTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
//        OnboardingPage(
//            modifier = Modifier.fillMaxSize()
//        )
//        SubscriptionPlansPage(
//            modifier = Modifier.fillMaxSize()
//        )
//        LoginPage(
//            modifier = Modifier.fillMaxSize()
//        )
//        SignUpPage(
//            modifier = Modifier.fillMaxSize()
//        )
//        SettingsPage(
//            modifier = Modifier.fillMaxSize()
//        )
//                        NavHost(
//                            navController = navController,
//                            startDestination = "/chat-list"
//                        ) {
//                            composable(route = "/chat-list") {
//                                ChatListPage(
//                                    onChatClicked = { chat ->
//                                        navController.currentBackStackEntry?.savedStateHandle?.set("chat", chat)
//                                        navController.navigate("/chat")
//                                    },
//                                    modifier = Modifier.fillMaxSize()
//                                )
//                            }
//                            composable(route = "/chat") { backstackEntry ->
//                                val chat = navController.previousBackStackEntry?.savedStateHandle?.get<Chat>("chat")
//                                ChatPage(
//                                    chat = chat,
//                                    onBackPressed = {
//                                        navController.popBackStack()
//                                    },
//                                    modifier = Modifier.fillMaxSize()
//                                )
//                            }
//                        }
                        FeedbackPage(
                            onBackClicked = {},
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}