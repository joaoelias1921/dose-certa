package br.pucpr.appdev.dosecerta.ui.data

import br.pucpr.appdev.dosecerta.models.Prescription

data class MyPrescriptionsUiState(
    val prescriptions: List<Prescription> = emptyList(),
    val prescriptionIdToDelete: String? = null,
    val isLoading: Boolean = true,
    val isSigningOut: Boolean = false,
    val isSignedOut: Boolean = false,
    val errorMessage: String? = null
)