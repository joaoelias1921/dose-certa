package br.pucpr.appdev.dosecerta.ui.screens.editprescription

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.pucpr.appdev.dosecerta.base.util.StringProvider
import br.pucpr.appdev.dosecerta.models.Medicine
import br.pucpr.appdev.dosecerta.models.Prescription
import br.pucpr.appdev.dosecerta.models.now
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.ui.data.EditPrescriptionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import br.pucpr.appdev.dosecerta.R
import java.util.Locale

class EditPrescriptionViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PrescriptionRepository,
    private val stringProvider: StringProvider,
): ViewModel() {
    private val prescriptionId: String = checkNotNull(savedStateHandle.get<String>("prescriptionId"))
    private val _originalData = repository.getPrescriptionById(prescriptionId)
        .filterNotNull()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)
    private val _uiState = MutableStateFlow(EditPrescriptionUiState(isLoading = true))
    val uiState: StateFlow<EditPrescriptionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _originalData.firstOrNull()?.let { initialPrescription ->
                _uiState.update {
                    it.copy(
                        prescriptionId = initialPrescription.id,
                        prescriptionName = initialPrescription.name,
                        prescriptionMedicines = initialPrescription.medicines,
                        userId = initialPrescription.userId,
                        isLoading = false,
                        createdAt = initialPrescription.createdAt
                    )
                }
            } ?: run {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = stringProvider.getString(
                        R.string.error_prescription_not_found
                    ))
                }
            }
        }
    }

    fun toggleNewMedicineModal() {
        _uiState.update { it.copy(isCreatingMedicine = !it.isCreatingMedicine) }
    }

    fun toggleTimeToTakePickerModal() {
        _uiState.update { it.copy(isPickingTimeToTake = !it.isPickingTimeToTake) }
    }

    fun setPrescriptionName(value: String) {
        _uiState.update { it.copy(prescriptionName = value) }
    }

    fun setNewMedicineName(value: String) {
        _uiState.update {
            it.copy(newMedicine = it.newMedicine.copy(name = value))
        }
    }

    fun setNewMedicineDosage(value: String) {
        _uiState.update {
            it.copy(newMedicine = it.newMedicine.copy(dosage = value))
        }
    }

    fun setNewMedicineFrequency(value: String) {
        _uiState.update {
            it.copy(newMedicine = it.newMedicine.copy(frequency = value))
        }
    }

    fun setNewMedicineObservations(value: String) {
        _uiState.update {
            it.copy(newMedicine = it.newMedicine.copy(observations = value))
        }
    }

    fun addNewMedicine(hour: Int, minute: Int) {
        _uiState.update { currentState ->
            val formattedHour = String.format(Locale.ROOT, "%02d", hour)
            val formattedMinute = String.format(Locale.ROOT, "%02d", minute)
            val newMedicineItem = currentState.newMedicine.copy(
                timeToTake = "${formattedHour}:${formattedMinute}"
            )
            val updatedList = currentState.prescriptionMedicines + newMedicineItem
            currentState.copy(
                prescriptionMedicines = updatedList,
                newMedicine = Medicine(),
                isCreatingMedicine = false,
                isPickingTimeToTake = false
            )
        }
    }

    fun resetDuplicatedMedicineError() {
        _uiState.update { it.copy(duplicatedMedicineError = false) }
    }

    fun chooseTimeToTake() {
        val currentState = uiState.value
        val newMedicineName = currentState.newMedicine.name.trim()
        val alreadyExists = currentState.prescriptionMedicines.any {
            it.name.trim().equals(newMedicineName, ignoreCase = true)
        }

        if (alreadyExists) {
            _uiState.update { it.copy(duplicatedMedicineError = true) }
            return
        }

        toggleNewMedicineModal()
        toggleTimeToTakePickerModal()
    }

    fun removeMedicineAt(selectedIndex: Int) {
        _uiState.update { currentState ->
            val updatedList = currentState.prescriptionMedicines.toMutableList()
            if (selectedIndex in updatedList.indices) updatedList.removeAt(selectedIndex)
            currentState.copy(prescriptionMedicines = updatedList)
        }
    }

    fun saveEditedPrescription() {
        val currentState = uiState.value
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                repository.updatePrescription(Prescription(
                    id = currentState.prescriptionId,
                    name = currentState.prescriptionName,
                    medicines = currentState.prescriptionMedicines,
                    userId = currentState.userId,
                    createdAt = currentState.createdAt,
                    updatedAt = now()
                ))
                _uiState.update { it.copy(isSaving = false, isSaved = true) }
            } catch (_:Exception) {
                _uiState.update { it.copy(isLoading = false) }
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
                    EditPrescriptionViewModel(
                        this.createSavedStateHandle(),
                        repository,
                        stringProvider
                    )
                }
            }
    }
}