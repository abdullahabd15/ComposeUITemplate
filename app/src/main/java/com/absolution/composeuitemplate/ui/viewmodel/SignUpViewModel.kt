package com.absolution.composeuitemplate.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun onTogglePasswordClicked(isPasswordVisible: Boolean) {
        _uiState.update {
            it.copy(isPasswordVisible = isPasswordVisible)
        }
    }

    fun onToggleConfirmPasswordClicked(isConfirmPasswordVisible: Boolean) {
        _uiState.update {
            it.copy(isConfirmPasswordVisible = isConfirmPasswordVisible)
        }
    }

    fun onTncAccepted(tncAccepted: Boolean) {
        _uiState.update {
            it.copy(tncAccepted = tncAccepted)
        }
    }

    fun signUp() {
        // TODO: Implement sign up logic
    }
}

data class SignUpUiState(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val tncAccepted: Boolean = false,
) {
    val isFormValid: Boolean
        get() = validateName(name)
                && validateEmail(email)
                && validatePassword(password)
                && validateConfirmPassword(confirmPassword, password)
                && tncAccepted

    val nameError: String?
        get() = if (!validateName(name) && name != null) "Name is required" else null

    val emailError: String?
        get() = if (!validateEmail(email) && email != null) "Invalid email format" else null

    val passwordError: String?
        get() = if (!validatePassword(password) && password != null) "Password must be at least 6 characters" else null

    val confirmPasswordError: String?
        get() = if (!validateConfirmPassword(confirmPassword, password) && confirmPassword != null)
            "Passwords do not match"
        else
            null

    private fun validateName(name: String?): Boolean = name?.isNotBlank() == true
    private fun validateEmail(email: String?): Boolean =
        !email.isNullOrBlank() && Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)

    private fun validatePassword(password: String?): Boolean =
        !password.isNullOrBlank() && password.length >= 6

    private fun validateConfirmPassword(confirmPassword: String?, password: String?): Boolean =
        !confirmPassword.isNullOrBlank() && confirmPassword == password
}