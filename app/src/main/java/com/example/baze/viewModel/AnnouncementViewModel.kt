package com.example.baze.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baze.data.model.Announcements
import com.example.baze.data.model.UserProfile
import com.google.firebase.firestore.Query

class AnnouncementViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _announcements = MutableLiveData<List<Announcements>>()
    val announcements: LiveData<List<Announcements>> = _announcements


    fun fetchAnnouncements(selectedCourses: List<String>) {
        if (selectedCourses.isEmpty()) return

        db.collection("announcements")
            .whereIn("courseId", selectedCourses) // ✅ Filter by selected courses
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener

                val list = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Announcements::class.java)
                }
                _announcements.value = list
            }
    }

    fun postAnnouncement(
        message: String,
        courseId: String,
        facultyName: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val announcement = Announcements(
            message = message,
            courseId = courseId,
            courses = listOf(courseId), // or pass real list
            facultyName = facultyName,
            timestamp = System.currentTimeMillis()
        )

        db.collection("announcements")
            .add(announcement)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }




}
