package br.pucpr.appdev.dosecerta.ui.data

import br.pucpr.appdev.dosecerta.models.Medicine

data class NewPrescriptionUiState(
    val prescriptionName: String = "",
    val prescriptionMedicines: List<Medicine> = emptyList(),
    val newMedicine: Medicine = Medicine(),
    val isCreatingMedicine: Boolean = false,
    val isLoading: Boolean = false,
    val isCreated: Boolean = false
)