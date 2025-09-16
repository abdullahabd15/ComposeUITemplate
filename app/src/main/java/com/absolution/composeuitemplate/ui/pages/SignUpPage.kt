package com.absolution.composeuitemplate.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.absolution.composeuitemplate.ui.viewmodel.SignUpUiState
import com.absolution.composeuitemplate.ui.viewmodel.SignUpViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignUpUi(
        uiState = uiState,
        onNameChanged = viewModel::onNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        onTogglePasswordClicked = viewModel::onTogglePasswordClicked,
        onToggleConfirmPasswordClicked = viewModel::onToggleConfirmPasswordClicked,
        onTncAccepted = viewModel::onTncAccepted,
        onSignUp = viewModel::signUp,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun SignUpUi(
    uiState: SignUpUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onTogglePasswordClicked: (Boolean) -> Unit,
    onToggleConfirmPasswordClicked: (Boolean) -> Unit,
    onTncAccepted: (Boolean) -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val annotatedText = buildAnnotatedString {
        append("I've read and agree with the ")

        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Terms and Conditions")
        }
        pop()

        append(" and the ")

        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy")
        }
        pop()

        append(".")
    }

    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.height(36.dp))
            Text(
                "Sign up",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text("Create an account to get started", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(24.dp))
            Text(
                "Name",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.name.orEmpty(),
                onValueChange = onNameChanged,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                placeholder = {
                    Text("Type your name")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (uiState.nameError?.isNotBlank() == true) Color.Red else Color.Blue,
                    unfocusedBorderColor = if (uiState.nameError?.isNotBlank() == true) Color.Red else Color.Gray,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            Text(uiState.nameError.orEmpty(), color = Color.Red)
            Spacer(Modifier.height(4.dp))
            Text(
                "Email Address",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.email.orEmpty(),
                onValueChange = onEmailChanged,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                placeholder = {
                    Text("name@email.com")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (uiState.emailError?.isNotEmpty() == true) Color.Red else Color.Blue,
                    unfocusedBorderColor = if (uiState.emailError?.isNotEmpty() == true) Color.Red else Color.Gray,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            Text(uiState.emailError.orEmpty(), color = Color.Red)
            Spacer(Modifier.height(4.dp))
            Text(
                "Password",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            PasswordField(
                value = uiState.password.orEmpty(),
                onValueChange = onPasswordChanged,
                isPasswordVisible = uiState.isPasswordVisible,
                onTogglePasswordClicked = onTogglePasswordClicked,
                placeholder = "Create a password",
                isError = uiState.passwordError?.isNotBlank() == true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            Text(uiState.passwordError.orEmpty(), color = Color.Red)
            Spacer(Modifier.height(4.dp))
            PasswordField(
                value = uiState.confirmPassword.orEmpty(),
                onValueChange = onConfirmPasswordChanged,
                isPasswordVisible = uiState.isConfirmPasswordVisible,
                onTogglePasswordClicked = onToggleConfirmPasswordClicked,
                placeholder = "Confirm password",
                isError = uiState.confirmPasswordError != null,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            Text(uiState.confirmPasswordError.orEmpty(), color = Color.Red)
            Spacer(Modifier.height(12.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.tncAccepted,
                    onCheckedChange = onTncAccepted,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Blue
                    )
                )
                Spacer(Modifier.width(8.dp))
                @Suppress("DEPRECATION")
                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = {
                        annotatedText.getStringAnnotations(it, it)
                            .firstOrNull()?.let { stringAnnotation ->
                                when (stringAnnotation.item) {
                                    "TERMS" -> {}
                                    "PRIVACY" -> {}
                                }
                            }
                    }
                )
            }
            Spacer(Modifier.height(16.dp))
            ElevatedButton(
                onClick = onSignUp,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = uiState.isFormValid,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                )
            ) {
                Text("Sign Up")
            }
        }
    }
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordClicked: (Boolean) -> Unit,
    placeholder: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(placeholder)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (!isPasswordVisible) {
                IconButton(onClick = { onTogglePasswordClicked(true) }) {
                    Image(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = null,
                    )
                }
            } else {
                IconButton(onClick = { onTogglePasswordClicked(false) }) {
                    Image(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) Color.Red else Color.Blue,
            unfocusedBorderColor = if (isError) Color.Red else Color.Gray,
        ),
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
@Preview
private fun SignUpPreview() {
    SignUpUi(
        uiState = SignUpUiState(
            tncAccepted = true
        ),
        onNameChanged = {},
        onEmailChanged = {},
        onPasswordChanged = {},
        onConfirmPasswordChanged = {},
        onTogglePasswordClicked = {},
        onToggleConfirmPasswordClicked = {},
        onTncAccepted = {},
        onSignUp = {},
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    )
}