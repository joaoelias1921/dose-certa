package br.pucpr.appdev.dosecerta.ui.data

data class AuthUiState(
    val fullName: String = "",
    val fullNameError: String = "",
    val dateOfBirth: String = "",
    val dateOfBirthError: String = "",
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoginMode: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false
)