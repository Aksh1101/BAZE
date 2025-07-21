package com.example.baze.data.model

data class UserProfile( val email: String = "",
                        val mobileNumber: String = "",
                        val role: String = "",
                        val firstName: String = "",
                        val lastName: String = "",
                        val selectedCourses: List<String> = emptyList())