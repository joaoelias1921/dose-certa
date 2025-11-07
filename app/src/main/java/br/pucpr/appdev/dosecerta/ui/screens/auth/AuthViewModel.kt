package br.pucpr.appdev.dosecerta.ui.screens.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.repositories.UserRepository
import br.pucpr.appdev.dosecerta.ui.data.AuthUiState
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import br.pucpr.appdev.dosecerta.R

class UserDataConstants {
    companion object {
        const val VALID_FIELD = ""
        const val INVALID_FIELD_ERROR = "error_invalid"
        const val EMPTY_FIELD_ERROR = "error_empty"
        const val DOB_LENGTH = 8
    }
}

class AuthViewModel(
    private val userRepository: UserRepository,
    private val stringProvider: StringProvider
): ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email, emailError = validateEmail(email)) }
    }

    fun setPassword(password: String) {
        _uiState.update {
            it.copy(password = password, passwordError = validatePassword(password))
        }
    }

    fun setFullName(name: String) {
        _uiState.update { it.copy(fullName = name, fullNameError = validateName(name)) }
    }

    fun setDateOfBirth(dob: String) {
        _uiState.update { it.copy(dateOfBirth = dob, dateOfBirthError = validateDob(dob)) }
    }

    fun toggleAuthMode() {
        _uiState.update { it.copy( isLoginMode = !it.isLoginMode) }
        resetErrors()
    }

    fun resetErrors() {
        _uiState.update {
            it.copy(
                errorMessage = null,
                fullNameError = UserDataConstants.VALID_FIELD,
                dateOfBirthError = UserDataConstants.VALID_FIELD,
                emailError = UserDataConstants.VALID_FIELD,
                passwordError = UserDataConstants.VALID_FIELD
            )
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun authenticateUser() {
        val currentState = uiState.value
        if (!uiState.value.isLoginMode && !isRegistrationValid()) return
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                if (currentState.isLoginMode) {
                    userRepository.signInUser(
                        currentState.email,
                        currentState.password
                    )
                } else {
                    userRepository.registerUser(
                        email = currentState.email,
                        password = currentState.password,
                        fullName = currentState.fullName,
                        dateOfBirth = currentState.dateOfBirth
                    )
                }
                _uiState.update { it.copy(isLoading = false, isAuthenticated = true) }
            } catch (e: Exception) {
                val errorMsg = when (e) {
                    is FirebaseAuthWeakPasswordException -> stringProvider.getString(
                        R.string.error_use_stronger_password
                    )
                    is FirebaseAuthInvalidCredentialsException -> stringProvider.getString(
                        R.string.error_invalid_username_or_pass
                    )
                    is FirebaseAuthUserCollisionException -> stringProvider.getString(
                        R.string.error_email_already_in_use
                    )
                    else -> stringProvider.getString(
                        R.string.error_generic_try_again
                    )
                }
                _uiState.update { it.copy(isLoading = false, errorMessage = errorMsg) }
            }
        }
    }

    private fun isRegistrationValid(): Boolean {
        return validateName(uiState.value.fullName) == UserDataConstants.VALID_FIELD &&
            validateDob(uiState.value.dateOfBirth) == UserDataConstants.VALID_FIELD &&
            validateEmail(uiState.value.email) == UserDataConstants.VALID_FIELD &&
            validatePassword(uiState.value.password) == UserDataConstants.VALID_FIELD
    }

    private fun validateName(name: String): String {
        return when {
            name.isBlank() -> UserDataConstants.EMPTY_FIELD_ERROR
            name.length < 2 -> UserDataConstants.INVALID_FIELD_ERROR
            else -> UserDataConstants.VALID_FIELD
        }
    }

    private fun validateDob(dob: String): String {
        return when {
            dob.isBlank() -> UserDataConstants.EMPTY_FIELD_ERROR
            dob.length != UserDataConstants.DOB_LENGTH || !dob.all { it.isDigit() } ->
                UserDataConstants.INVALID_FIELD_ERROR
            else -> {
                val formattedDob =
                    "${dob.substring(0, 2)}/${dob.substring(2, 4)}/${dob.substring(4, 8)}"
                return try {
                    val parsedDate = LocalDate.parse(
                        formattedDob,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                    val today = LocalDate.now()
                    val inputAge = Period.between(parsedDate, today).years

                    if (inputAge < 18) {
                        UserDataConstants.INVALID_FIELD_ERROR
                    } else {
                        UserDataConstants.VALID_FIELD
                    }
                } catch (_: DateTimeParseException) {
                    UserDataConstants.INVALID_FIELD_ERROR
                }
            }
        }
    }

    private fun validateEmail(email: String): String {
        return when {
            email.isBlank() -> UserDataConstants.EMPTY_FIELD_ERROR
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                UserDataConstants.INVALID_FIELD_ERROR
            else -> UserDataConstants.VALID_FIELD
        }
    }

    private fun validatePassword(password: String): String {
        return when {
            password.isBlank() -> UserDataConstants.EMPTY_FIELD_ERROR
            password.length < 8 -> UserDataConstants.INVALID_FIELD_ERROR
            else -> UserDataConstants.VALID_FIELD
        }
    }

    companion object {
        fun factory(
            repository: UserRepository,
            stringProvider: StringProvider
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    AuthViewModel(repository, stringProvider)
                }
            }
    }
}

