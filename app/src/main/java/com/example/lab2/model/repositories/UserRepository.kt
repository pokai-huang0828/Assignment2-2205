package com.example.lab2.model.repositories

import android.util.Log
import com.example.lab2.model.entities.User
import com.example.lab2.model.responses.Resource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val _collection = FirebaseFirestore.getInstance().collection("users")
    private val TAG = "Debug"

    fun getUsers() = callbackFlow {
        val snapshotListener = _collection.addSnapshotListener { snapshot, error ->
            val response = if (error == null) {
                val users = mutableListOf<User>()

                snapshot?.let { snapshot ->
                    snapshot.documents.mapTo(users) {
                        mapDataToUser(it)
                    }

                    Resource.Success(users)
                }
            } else {
                Resource.Error("Failed to load users", error)
            }

            offer(response)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    suspend fun getUserById(id: String): Resource<Any?> {
        val response = _collection
            .document(id)
            .get()
            .await()

        if (response.exists())
            return Resource.Success(response.toObject(User::class.java))

        return Resource.Error("Could not find the user with the given Id.")
    }

    fun createUser(user: User, onResponse: (Resource<*>) -> Unit) {
        _collection.document(user.id).set(mapUserToData(user))
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot written with ID: ${user.id}")

                onResponse(Resource.Success(user.id))
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding user", e)

                onResponse(Resource.Error("Error adding user", e))
            }
    }

    fun updateUser(user: User, onResponse: (Resource<*>) -> Unit) {
        _collection.document(user.id).set(mapUserToData(user))
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")

                onResponse(Resource.Success(user.id))
            }
            .addOnFailureListener {
                    e -> Log.w(TAG, "Error updating document", e)

                onResponse(Resource.Error("Error updating user", e))
            }
    }

    fun deleteUser(user: User, onResponse: (Resource<*>) -> Unit) {
        _collection.document(user.id).delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")

                onResponse(Resource.Success(user.id))
            }
            .addOnFailureListener {
                    e -> Log.w(TAG, "Error deleting document", e)

                onResponse(Resource.Error("Error deleting user", e))
            }
    }

    private fun mapUserToData(user: User): HashMap<String, Any?>{
        return hashMapOf(
            "id" to user.id,
            "name" to user.name,
            "email" to user.email,
            "thumbnailUrl" to user.thumbnailUrl,
        )
    }

    private fun mapDataToUser(documentSnapshot: DocumentSnapshot): User {
        return User(
            id = documentSnapshot.id,
            name = documentSnapshot.getString("name") ?: "",
            email = documentSnapshot.getString("email") ?: "",
            thumbnailUrl = documentSnapshot.getString("thumbnailUrl") ?: "",
        )
    }
}