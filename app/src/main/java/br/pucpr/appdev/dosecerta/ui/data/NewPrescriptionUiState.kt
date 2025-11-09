package br.pucpr.appdev.dosecerta.ui.data

import br.pucpr.appdev.dosecerta.models.Medicine

data class NewPrescriptionUiState(
    val prescriptionName: String = "",
    val prescriptionMedicines: List<Medicine> = emptyList(),
    val newMedicine: Medicine = Medicine(),
    val duplicatedMedicineError: Boolean = false,
    val isCreatingMedicine: Boolean = false,
    val isPickingTimeToTake: Boolean = false,
    val selectedHourToTake: Int = 0,
    val selectedMinuteToTake: Int = 0,
    val isLoading: Boolean = false,
    val isCreated: Boolean = false
)