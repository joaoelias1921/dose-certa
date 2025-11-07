package br.pucpr.appdev.dosecerta.ui.data

import br.pucpr.appdev.dosecerta.models.Medicine

data class EditPrescriptionUiState(
    val userId: String = "",
    val prescriptionId: String = "",
    val prescriptionName: String = "",
    val prescriptionMedicines: List<Medicine> = emptyList(),
    val newMedicine: Medicine = Medicine(),
    val isCreatingMedicine: Boolean = false,
    val createdAt: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isSaving: Boolean = false
)