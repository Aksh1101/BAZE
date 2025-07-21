package com.example.baze.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baze.data.FirestoreRepository
import com.example.baze.data.model.UserProfile
import com.example.baze.data.model.Announcements
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AuthViewModel : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile: LiveData<UserProfile?> = _userProfile
    private val firestoreRepository = FirestoreRepository()
    private val _selectedCourses = MutableLiveData<List<String>>()
    val selectedCourse: LiveData<List<String>> = _selectedCourses
    private val _announcements = MutableLiveData<List<Announcements>>()
    val announcements: LiveData<List<Announcements>> = _announcements


    private val auth : FirebaseAuth = FirebaseAuth.getInstance()



    init {
        checkAuthStatus()
        fetchUserProfile()
    }

    fun checkAuthStatus(){
        val user = auth.currentUser
        if(auth.currentUser == null){
            _authState.value = AuthState.unauthenticated
        }else{
            _authState.value = AuthState.authenticated
            fetchUserProfile()
        }

    }

    fun login(email : String, password : String){

        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return
        }
        _authState.value =AuthState.loading

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _authState.value = AuthState.authenticated
                    fetchUserProfile()
                }else
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
            }
    }

    fun signup(email: String, password: String, mobileNumber: String, role: String) {
        _authState.value = AuthState.loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener

                val userProfile = UserProfile(
                    email = email,
                    mobileNumber = mobileNumber,
                    role = role,
                    firstName = "",
                    lastName = ""
                )

                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .set(userProfile)
                    .addOnSuccessListener {
                        _authState.value = AuthState.authenticated
                        fetchUserProfile()
                    }
                    .addOnFailureListener {
                        _authState.value = AuthState.Error("Profile creation failed: ${it.message}")
                    }
            }
            .addOnFailureListener {
                _authState.value = AuthState.Error("Signup failed: ${it.message}")
            }
    }


    fun fetchUserProfile() {
        val uid = auth.currentUser?.uid ?: return

        FirebaseFirestore.getInstance().collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                val profile = document.toObject(UserProfile::class.java)
                _userProfile.value = profile
                _selectedCourses.value = profile?.selectedCourses ?: emptyList()
            }
            .addOnFailureListener {
                _authState.value = AuthState.Error("Failed to fetch user profile: ${it.message}")
            }
    }


    fun loadRelevantAnnouncements() {
        val courseList = _selectedCourses.value ?: return

        firestoreRepository.getAnnouncementsForCourses(
            courseList,
            onSuccess = { _announcements.value = it },
            onFailure = { /* Handle error */ }
        )
    }

    fun makeAnnouncement(courses : String, message: String) {
        val courses = _selectedCourses.value ?: return
        val name = _userProfile.value?.firstName + " " + _userProfile.value?.lastName

        courses.forEach { courseId ->
            val announcement = Announcements(
                courses = courses,
                message = message,
                courseId = courseId,
                facultyName = name
            )
            firestoreRepository.postAnnouncement(
                announcement,
                onSuccess = { /* Optionally notify success */ },
                onFailure = { /* Show error */ }
            )
        }
    }







    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.unauthenticated
    }


}








sealed class  AuthState{
    object authenticated : AuthState()
    object unauthenticated : AuthState()
    object loading : AuthState()
    data class Error(val message : String) : AuthState()

}