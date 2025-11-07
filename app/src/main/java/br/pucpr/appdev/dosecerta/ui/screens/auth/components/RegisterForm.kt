package br.pucpr.appdev.dosecerta.ui.screens.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.pucpr.appdev.dosecerta.R
import br.pucpr.appdev.dosecerta.ui.data.AuthUiState
import br.pucpr.appdev.dosecerta.ui.screens.auth.UserDataConstants
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DisabledBlue
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue
import br.pucpr.appdev.dosecerta.ui.screens.auth.utils.DateVisualTransformation

@Composable
fun RegisterForm(
    uiState: AuthUiState,
    onChangeFullName: (name: String) -> Unit,
    onChangeDateOfBirth: (dob: String) -> Unit,
    onChangeEmail: (email: String) -> Unit,
    onChangePassword: (password: String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSignIn: () -> Unit,
    onToggleAuthMode: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
) {
    Text(
        text = stringResource(R.string.auth_create_account),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.auth_insert_basic_data),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(32.dp))

    uiState.errorMessage?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }

    OutlinedTextField(
        value = uiState.fullName,
        onValueChange = onChangeFullName,
        label = { Text(stringResource(R.string.auth_full_name)) },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.fullNameError.isNotBlank(),
        placeholder = {
            Text(
                text = stringResource(R.string.auth_name_placeholder)
            )
        },
        supportingText = {
            when (uiState.fullNameError) {
                UserDataConstants.EMPTY_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_name_cannot_be_empty
                    )
                )
                UserDataConstants.INVALID_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_name_please_use_valid_name
                    )
                )
                else -> Text(
                    text = stringResource(R.string.auth_name_supporting_text)
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DoseCertaBlue,
            focusedLabelColor = DoseCertaBlue,
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.dateOfBirth,
        onValueChange = { newValue ->
            if (newValue.length <= UserDataConstants.DOB_LENGTH) {
                onChangeDateOfBirth(newValue)
            }
        },
        label = { Text(text = stringResource(R.string.auth_dob)) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DoseCertaBlue,
            focusedLabelColor = DoseCertaBlue,
        ),
        isError = uiState.dateOfBirthError.isNotBlank(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = DateVisualTransformation("dd/dd/dddd"),
        placeholder = {
            Text(
                text = stringResource(R.string.auth_dob_placeholder)
            )
        },
        supportingText = {
            when (uiState.dateOfBirthError) {
                UserDataConstants.EMPTY_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_dob_cannot_be_empty
                    )
                )
                UserDataConstants.INVALID_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_dob_please_use_valid_date
                    )
                )
                else -> Text(
                    text = stringResource(R.string.auth_dob_supporting_text)
                )
            }
        },
        singleLine = true
    )
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.email,
        onValueChange = onChangeEmail,
        label = { Text(stringResource(R.string.auth_email)) },
        leadingIcon = {
            Icon(Icons.Default.Email, contentDescription = null)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.auth_email_placeholder)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DoseCertaBlue,
            focusedLabelColor = DoseCertaBlue,
        ),
        isError = uiState.emailError.isNotBlank(),
        supportingText = {
            when (uiState.emailError) {
                UserDataConstants.EMPTY_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_email_cannot_be_empty
                    )
                )
                UserDataConstants.INVALID_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_email_please_use_valid_email
                    )
                )
                else -> Text(
                    text = stringResource(R.string.auth_email_supporting_text)
                )
            }
        },
        singleLine = true
    )
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.password,
        onValueChange = onChangePassword,
        label = { Text(stringResource(R.string.auth_password)) },
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = null)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.auth_pass_placeholder)
            )
        },
        trailingIcon = {
            val image = if (uiState.isPasswordVisible)
                Icons.Default.Visibility
            else
                Icons.Default.VisibilityOff

            IconButton(onClick = { onTogglePasswordVisibility() }) {
                Icon(image, contentDescription = stringResource(
                    R.string.auth_password_visibility_btn_label)
                )
            }
        },
        visualTransformation = if (uiState.isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onSignIn()
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DoseCertaBlue,
            focusedLabelColor = DoseCertaBlue,
        ),
        isError = uiState.passwordError.isNotBlank(),
        supportingText = {
            when (uiState.passwordError) {
                UserDataConstants.EMPTY_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_pass_cannot_be_empty
                    )
                )
                UserDataConstants.INVALID_FIELD_ERROR -> Text(
                    text = stringResource(
                        R.string.auth_pass_please_use_valid_password
                    )
                )
                else -> Text(
                    text = stringResource(R.string.auth_pass_supporting_text)
                )
            }
        },
        singleLine = true
    )
    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = { onSignIn() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonColors(
            DoseCertaBlue,
            BackgroundLight,
            DisabledBlue,
            BackgroundLight
        ),
        shape = ShapeDefaults.Small,
        enabled = !uiState.isLoading &&
            uiState.fullName.isNotBlank() &&
            uiState.dateOfBirth.isNotBlank() &&
            uiState.email.isNotBlank() &&
            uiState.password.isNotBlank()

    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(text = stringResource(R.string.auth_sign_up))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "${stringResource(R.string.auth_already_registered)} ")
        Text(
            text = stringResource(R.string.auth_already_registered_login_now),
            color = DoseCertaBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = { onToggleAuthMode() }
            )
        )
    }
}