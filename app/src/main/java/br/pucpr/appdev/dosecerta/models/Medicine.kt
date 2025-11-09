package br.pucpr.appdev.dosecerta.models

import kotlinx.serialization.Serializable

@Serializable
data class Medicine(
    val name: String = "",
    val dosage: String = "",
    val frequency: String = "",
    val timeToTake: String = "",
    val observations: String = "",
    val alarmActive: Boolean = false,
)
