package br.pucpr.appdev.dosecerta.ui.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.pucpr.appdev.dosecerta.repositories.AppPreferencesRepository
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val appPreferencesRepository: AppPreferencesRepository
): ViewModel() {
    fun userBeginsJourney() {
        viewModelScope.launch {
            appPreferencesRepository.setHasAccessedBefore(true)
        }
    }

    companion object {
        fun factory(
            appPreferencesRepository: AppPreferencesRepository
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    WelcomeViewModel(appPreferencesRepository)
                }
            }
    }
}