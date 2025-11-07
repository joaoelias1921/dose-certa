package br.pucpr.appdev.dosecerta.ui.screens.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.pucpr.appdev.dosecerta.base.Routes
import br.pucpr.appdev.dosecerta.repositories.AppPreferencesRepository
import br.pucpr.appdev.dosecerta.repositories.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class StartupViewModel(
    userRepository: UserRepository,
    appPreferencesRepository: AppPreferencesRepository
): ViewModel() {

    val startupDestination: StateFlow<Routes?> = combine(
        userRepository.getAuthState(),
        appPreferencesRepository.hasAccessedBefore
    ) { user, hasAccessedBefore ->
        if (user != null) return@combine Routes.MyPrescriptions
        if (hasAccessedBefore) return@combine Routes.Auth
        Routes.Welcome
    }.stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(5000)
    )

    companion object {
        fun factory(
            userRepository: UserRepository,
            appPreferencesRepository: AppPreferencesRepository
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    StartupViewModel(userRepository, appPreferencesRepository)
                }
            }
    }
}