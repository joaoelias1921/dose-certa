package br.pucpr.appdev.dosecerta.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Prescription(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    var medicines: List<Medicine> = listOf(),
    val createdAt: String = now(),
    var updatedAt: String = now()
)

fun now(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
}