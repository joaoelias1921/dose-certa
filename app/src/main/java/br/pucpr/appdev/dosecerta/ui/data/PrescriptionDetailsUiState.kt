package br.pucpr.appdev.dosecerta.ui.data

import br.pucpr.appdev.dosecerta.models.Prescription

data class PrescriptionDetailsUiState(
    val prescription: Prescription? = null,
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val showDeleteModal: Boolean = false,
    val isDeleted: Boolean = false
)