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
import br.pucpr.appdev.dosecerta.ui.theme.BackgroundLight
import br.pucpr.appdev.dosecerta.ui.theme.DisabledBlue
import br.pucpr.appdev.dosecerta.ui.theme.DoseCertaBlue

@Composable
fun LoginForm(
    uiState: AuthUiState,
    onChangeEmail: (email: String) -> Unit,
    onChangePassword: (password: String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSignIn: () -> Unit,
    onToggleAuthMode: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
) {
    Text(
        text = stringResource(R.string.auth_welcome_back),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text =  stringResource(R.string.auth_sign_in_to_continue),
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
        value = uiState.email,
        onValueChange = onChangeEmail,
        label = { Text(stringResource(R.string.auth_email)) },
        leadingIcon = {
            Icon(Icons.Default.Email, contentDescription = null)
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
        singleLine = true
    )
    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = { onSignIn() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = ShapeDefaults.Small,
        colors = ButtonColors(
            DoseCertaBlue,
            BackgroundLight,
            DisabledBlue,
            BackgroundLight
        ),
        enabled = !uiState.isLoading &&
                uiState.emailError.isBlank() &&
                uiState.password.isNotEmpty()
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(text = stringResource(R.string.auth_login))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "${stringResource(R.string.auth_not_registered)} ")
        Text(
            text = stringResource(R.string.auth_not_registered_create_now),
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