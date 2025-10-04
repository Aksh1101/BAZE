package com.example.baze.data

import com.example.baze.data.model.UserProfile
import com.example.baze.data.model.Announcements
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    fun saveUserCourses(
        courses: List<String>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return
        val userDoc = firestore.collection("users").document(uid)



        userDoc.update("selectedCourses", courses)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }



    fun getUserCourses(
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return
        val userDoc = firestore.collection("users").document(uid)

        userDoc.get()
            .addOnSuccessListener { document ->
                val courses = document.get("selectedCourses") as? List<String> ?: emptyList()
                onSuccess(courses)
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun saveUserProfile(profile: UserProfile, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        val userDoc = firestore.collection("users").document(uid)

        userDoc.set(profile, SetOptions.merge())  // this will overwrite all fields if document already exists
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
    fun getUserProfile(
        onSuccess: (UserProfile) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return
        val userDoc = firestore.collection("users").document(uid)

        userDoc.get()
            .addOnSuccessListener { document ->
                val profile = document.toObject(UserProfile::class.java)
                if (profile != null) {
                    onSuccess(profile)
                }
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun postAnnouncement(announcement: Announcements, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("announcements")
            .add(announcement)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }
    fun getAnnouncementsForCourses(
        courseIds: List<String>,
        onSuccess: (List<Announcements>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        FirebaseFirestore.getInstance()
            .collection("announcements")
            .whereIn("courseId", courseIds.take(10))
            .get()
            .addOnSuccessListener { snapshot ->
                val announcements = snapshot.toObjects(Announcements::class.java)
                onSuccess(announcements.sortedByDescending { it.timestamp })
            }
            .addOnFailureListener { onFailure(it) }
    }



}