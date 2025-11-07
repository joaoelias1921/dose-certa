package br.pucpr.appdev.dosecerta.repositories

import br.pucpr.appdev.dosecerta.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun registerUser(
        email: String,
        password: String,
        fullName: String,
        dateOfBirth: String
    ) {
        val userCredential = auth.createUserWithEmailAndPassword(
            email, password
        ).await()
        val user = userCredential.user

        if (user != null) {
            val newUser = User(
                uid = user.uid,
                email = user.email ?: "",
                name = fullName,
                dateOfBirth = dateOfBirth
            )
            firestore
                .collection("users")
                .document(user.uid)
                .set(newUser)
                .await()
        }
    }

    suspend fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    fun getAuthState(): Flow<FirebaseUser?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose { auth.removeAuthStateListener(authStateListener) }
    }

    fun signOutUser() {
        auth.signOut()
    }

    suspend fun getUserData(): User? {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            val document = firestore
                .collection("users")
                .document(currentUser.uid)
                .get()
                .await()
            document.toObject(User::class.java)
        } else {
            null
        }
    }
}