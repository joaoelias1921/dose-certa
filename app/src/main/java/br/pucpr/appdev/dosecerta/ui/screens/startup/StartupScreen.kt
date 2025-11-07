package br.pucpr.appdev.dosecerta.ui.screens.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.appdev.dosecerta.base.Routes
import br.pucpr.appdev.dosecerta.repositories.AppPreferencesRepository
import br.pucpr.appdev.dosecerta.repositories.UserRepository
import br.pucpr.appdev.dosecerta.ui.components.ScreenLoadingIndicator

@Composable
fun StartupScreen(
    userRepository: UserRepository,
    appPreferencesRepository: AppPreferencesRepository,
    onStartup: (destination: String) -> Unit
) {
    val viewModel: StartupViewModel = viewModel(
        factory = StartupViewModel.factory(userRepository, appPreferencesRepository)
    )
    val startupDestination by viewModel.startupDestination.collectAsState()

    LaunchedEffect(startupDestination) {
        startupDestination?.route?.let { route ->
            onStartup(startupDestination?.route ?: Routes.Welcome.route)
        }
    }

    if (startupDestination == null) { ScreenLoadingIndicator() }
}