package br.pucpr.appdev.dosecerta.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.repositories.UserRepository
import br.pucpr.appdev.dosecerta.ui.screens.auth.components.LoginForm
import br.pucpr.appdev.dosecerta.ui.screens.auth.components.RegisterForm

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(
    repository: UserRepository,
    stringProvider: StringProvider,
    onAuthSuccess: () -> Unit = {},
) {
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.factory(repository, stringProvider)
    )
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) onAuthSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        if (uiState.isLoginMode) {
            LoginForm(
                uiState = uiState,
                onChangeEmail = viewModel::setEmail,
                onChangePassword = viewModel::setPassword,
                onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
                onSignIn = viewModel::authenticateUser,
                onToggleAuthMode = viewModel::toggleAuthMode,
                focusManager = focusManager,
                keyboardController = keyboardController,
            )
            return
        }
        RegisterForm(
            uiState = uiState,
            onChangeFullName = viewModel::setFullName,
            onChangeDateOfBirth = viewModel::setDateOfBirth,
            onChangeEmail = viewModel::setEmail,
            onChangePassword = viewModel::setPassword,
            onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
            onSignIn = viewModel::authenticateUser,
            onToggleAuthMode = viewModel::toggleAuthMode,
            focusManager = focusManager,
            keyboardController = keyboardController,
        )
    }
}