package com.absolution.composeuitemplate.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.absolution.composeuitemplate.R
import com.absolution.composeuitemplate.ui.components.ImagePlaceholder
import com.absolution.composeuitemplate.ui.viewmodel.LoginUiState
import com.absolution.composeuitemplate.ui.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginUi(
        uiState = uiState,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onForgotPasswordClicked = {},
        onLoginClicked = viewModel::login,
        onRegisterClicked = {},
        onTogglePasswordClicked = viewModel::onTogglePasswordClicked,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun LoginUi(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onTogglePasswordClicked: (Boolean) -> Unit,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val softKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier.background(color = Color.White).verticalScroll(scrollState)
    ) {
        ImagePlaceholder(
            modifier = Modifier.fillMaxWidth().aspectRatio(3f / 2.5f)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Welcome!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.email.orEmpty(),
            onValueChange = onEmailChanged,
            label = { Text("Email Address") },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.password.orEmpty(),
            onValueChange = onPasswordChanged,
            label = { Text("Password") },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onLoginClicked()
                    softKeyboardController?.hide()
                }
            ),
            singleLine = true,
            visualTransformation = if (!uiState.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { onTogglePasswordClicked(!uiState.isPasswordVisible) }) {
                    Image(
                        imageVector = if (!uiState.isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onForgotPasswordClicked) {
            Text(
                "Forgot password?",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Blue
            )
        }
        Spacer(Modifier.height(8.dp))
        ElevatedButton(
            onClick = onLoginClicked,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
            )
        ) {
            Text("Login")
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Not a member? ")
            TextButton(
                onClick = onRegisterClicked,
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text(
                    "Register now",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Or continue with",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            IconButton(onClick = {

            }) {
                Image(
                    painter = painterResource(R.drawable.google_circle_button),
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = {

            }) {
                Image(
                    painter = painterResource(R.drawable.apple_circle_button),
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = {

            }) {
                Image(
                    painter = painterResource(R.drawable.facebook_circle_button),
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
@Preview
private fun LoginPreview() {
    LoginUi(
        uiState = LoginUiState(
            password = "kdjfhsk",
            isPasswordVisible = false
        ),
        onEmailChanged = {},
        onPasswordChanged = {},
        onLoginClicked = {},
        onForgotPasswordClicked = {},
        onRegisterClicked = {},
        onTogglePasswordClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}