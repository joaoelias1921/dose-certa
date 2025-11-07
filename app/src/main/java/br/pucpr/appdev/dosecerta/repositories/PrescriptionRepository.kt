package br.pucpr.appdev.dosecerta.repositories

import br.pucpr.appdev.dosecerta.models.Prescription
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PrescriptionRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val prescriptionsCollection = firestore.collection("prescriptions")

    fun getAllPrescriptions(): Flow<List<Prescription>> = callbackFlow {
        val currentUser = getCurrentUser()
        val listenerRegistration = prescriptionsCollection
            .whereEqualTo("userId", currentUser.uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val prescriptions = snapshot.toObjects(Prescription::class.java)
                    trySend(prescriptions)
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

    suspend fun createPrescription(prescription: Prescription) {
        val currentUser = getCurrentUser()
        val docId = UUID.randomUUID().toString()
        val finalPrescription = prescription.copy(
            id = docId,
            userId = currentUser.uid,
        )
        prescriptionsCollection.document(docId).set(finalPrescription).await()
    }

    suspend fun updatePrescription(prescription: Prescription) {
        val currentUser = auth.currentUser ?: throw Exception("Usuário não autenticado")
        if (prescription.userId != currentUser.uid) {
            throw SecurityException("Você não tem permissão para alterar este recurso")
        }
        prescriptionsCollection
            .document(prescription.id)
            .set(prescription)
            .await()
    }

    suspend fun deletePrescription(prescriptionId: String) {
        prescriptionsCollection.document(prescriptionId).delete().await()
    }

    fun getPrescriptionById(id: String): Flow<Prescription?> = callbackFlow {
        val listenerRegistration = prescriptionsCollection
            .document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot == null || !snapshot.exists()) {
                    trySend(null)
                    return@addSnapshotListener
                }
                trySend(snapshot.toObject(Prescription::class.java))
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    private fun getCurrentUser(): FirebaseUser {
        return auth.currentUser ?: throw Exception("Usuário não autenticado")
    }
}