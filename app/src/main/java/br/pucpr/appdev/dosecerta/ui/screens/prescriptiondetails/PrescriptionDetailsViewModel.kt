package br.pucpr.appdev.dosecerta.ui.screens.prescriptiondetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.ui.data.PrescriptionDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import br.pucpr.appdev.dosecerta.R

class PrescriptionDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    val repository: PrescriptionRepository,
    private val stringProvider: StringProvider
): ViewModel() {
    val prescriptionId: String = checkNotNull(savedStateHandle.get<String>("prescriptionId"))
    private val _uiEvents = MutableStateFlow(PrescriptionDetailsUiState())

    val uiState: StateFlow<PrescriptionDetailsUiState> = combine(
        repository.getPrescriptionById(prescriptionId),
        _uiEvents
    ) { prescription, events ->
        PrescriptionDetailsUiState(
            prescription = prescription,
            isLoading = false,
            showDeleteModal = events.showDeleteModal,
            isDeleted = events.isDeleted,
            errorMessage = events.errorMessage
        )
    }.catch { throwable ->
        emit(
            PrescriptionDetailsUiState(
                isLoading = false,
                errorMessage =
                    "${stringProvider.getString(
                        R.string.error_fetching_single_prescription
                    )}: ${throwable.localizedMessage}"
            )
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = PrescriptionDetailsUiState(isLoading = true),
        started = SharingStarted.WhileSubscribed(5000L)
    )

    fun openDeletePrescriptionModal() {
        _uiEvents.update { it.copy(showDeleteModal = true) }
    }

    fun closeDeletePrescriptionModal() {
        _uiEvents.update { it.copy(showDeleteModal = false) }
    }

    fun deletePrescription() {
        viewModelScope.launch {
            try {
                repository.deletePrescription(prescriptionId)
                _uiEvents.update { it.copy(isDeleted = true, showDeleteModal = false) }
            } catch (_: Exception) {
                _uiEvents.update { it.copy(
                    errorMessage = stringProvider.getString(
                        R.string.error_deleting_prescription
                    ),
                    showDeleteModal = false
                )}
            }
        }
    }

    fun updateMedicineAlarmStatusToActive(medicineName: String) {
        viewModelScope.launch {
            try {
                repository.activateMedicineAlarmStatus(medicineName, prescriptionId)
            } catch (e: Exception) {
                Log.d("TESTE", e.message ?: "")
                _uiEvents.update { it.copy(
                    errorMessage = stringProvider.getString(
                        R.string.error_updating_alarm_status
                    ),
                )}
            }
        }
    }

    companion object {
        fun factory(
            repository: PrescriptionRepository,
            stringProvider: StringProvider
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    PrescriptionDetailsViewModel(
                        this.createSavedStateHandle(),
                        repository,
                        stringProvider
                    )
                }
            }
    }
}