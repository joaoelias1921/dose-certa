package br.pucpr.appdev.dosecerta.ui.screens.newprescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.pucpr.appdev.dosecerta.models.Medicine
import br.pucpr.appdev.dosecerta.models.Prescription
import br.pucpr.appdev.dosecerta.repositories.PrescriptionRepository
import br.pucpr.appdev.dosecerta.ui.data.NewPrescriptionUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class NewPrescriptionViewModel(val repository: PrescriptionRepository): ViewModel() {
    private val _uiState = MutableStateFlow(NewPrescriptionUiState(
        selectedHourToTake = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        selectedMinuteToTake = Calendar.getInstance().get(Calendar.MINUTE)
    ))
    val uiState: StateFlow<NewPrescriptionUiState> = _uiState.asStateFlow()

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
                timeToTake = "${formattedHour}:${formattedMinute}",
                alarmActive = false
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


    fun saveNewPrescription() {
        val currentState = uiState.value
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val minimumDelayJob = launch { delay(500) }
            try {
                repository.createPrescription(
                    Prescription(
                        name = currentState.prescriptionName,
                        medicines = currentState.prescriptionMedicines,
                    )
                )
                minimumDelayJob.join()
                _uiState.update { it.copy(isLoading = false, isCreated = true) }
            } catch (_:Exception) {
                minimumDelayJob.join()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        fun factory(repository: PrescriptionRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    NewPrescriptionViewModel(repository)
                }
            }
    }
}