package com.example.baze.data.model

data class Announcements(
    val courses: List<String> = listOf(),
    val message: String = "",
    val courseId: String = "", // should match course names like "Math101"
    val facultyName: String = "",
    val timestamp: Long = System.currentTimeMillis()
)