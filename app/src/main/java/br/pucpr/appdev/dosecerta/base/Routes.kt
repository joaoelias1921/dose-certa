package br.pucpr.appdev.dosecerta.base

sealed class Routes(val route: String) {
    data object Startup: Routes("startup")
    data object Welcome: Routes("welcome")
    data object Auth: Routes("auth")
    data object MyPrescriptions: Routes("myPrescriptions")
    data object PrescriptionDetails: Routes("prescriptionDetails")
    data object NewPrescription: Routes("newPrescription")
    data object EditPrescription: Routes("editPrescription")
}