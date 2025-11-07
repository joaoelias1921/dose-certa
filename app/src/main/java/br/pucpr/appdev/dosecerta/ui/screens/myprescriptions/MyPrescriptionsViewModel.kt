package br.pucpr.appdev.dosecerta.ui.screens.myprescriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.repositories.UserRepository
import br.pucpr.appdev.dosecerta.ui.data.MyPrescriptionsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import br.pucpr.appdev.dosecerta.R

class MyPrescriptionsViewModel(
    private val prescriptionsRepository: PrescriptionRepository,
    private val userRepository: UserRepository,
    private val stringProvider: StringProvider
): ViewModel() {
    private val _uiState = MutableStateFlow(MyPrescriptionsUiState())
    val uiState: StateFlow<MyPrescriptionsUiState> = _uiState.asStateFlow()

    init { collectPrescriptionsFlow() }

    private fun collectPrescriptionsFlow() {
        prescriptionsRepository.getAllPrescriptions()
            .onEach { prescriptions ->
                _uiState.update { currentState ->
                    currentState.copy(
                        prescriptions = prescriptions,
                        isSigningOut = false,
                        isSignedOut = false,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .catch { e ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.message ?: stringProvider.getString(
                            R.string.error_fetching_prescriptions
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun deletePrescription() {
        viewModelScope.launch {
            try {
                val prescriptionIdToDelete = _uiState.value.prescriptionIdToDelete
                if (prescriptionIdToDelete == null) return@launch
                prescriptionsRepository.deletePrescription(prescriptionIdToDelete)
            } catch (_: Exception) {
                throw Exception(stringProvider.getString(
                    R.string.error_deleting_prescription)
                )
            }
        }
        closeDeletePrescriptionModal()
    }

    fun openDeletePrescriptionModal(prescriptionId: String) {
        _uiState.update { it.copy(prescriptionIdToDelete = prescriptionId) }
    }

    fun closeDeletePrescriptionModal() {
        _uiState.update { it.copy(prescriptionIdToDelete = null) }
    }

    fun toggleSignOutConfirmModal() {
        _uiState.update { it.copy(isSigningOut = !it.isSigningOut) }
    }

    fun performLogout() {
        viewModelScope.launch {
            try {
                userRepository.signOutUser()
                toggleSignOutConfirmModal()
                _uiState.update { it.copy(isSignedOut = true) }
            } catch (_: Exception) {
                throw Exception(stringProvider.getString(
                    R.string.error_signout_user)
                )
            }
        }
    }

    companion object {
        fun factory(
            prescriptionsRepository: PrescriptionRepository,
            userRepository: UserRepository,
            stringProvider: StringProvider
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    MyPrescriptionsViewModel(
                        prescriptionsRepository,
                        userRepository,
                        stringProvider
                    )
                }
            }
    }
}