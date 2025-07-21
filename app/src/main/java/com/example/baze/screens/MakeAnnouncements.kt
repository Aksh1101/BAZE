package com.example.baze.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.baze.R
import com.example.baze.data.courseList
import com.example.baze.viewModel.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeAnnouncements(navHostController: NavHostController, authViewModel: AuthViewModel) {

    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val userProfile by authViewModel.userProfile.observeAsState()
    val userCourses = userProfile?.selectedCourses ?: emptyList()

    var expanded by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf("") }
    var announcementText by remember { mutableStateOf("") }

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()


    LaunchedEffect(Unit) {
        authViewModel.fetchUserProfile()
    }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
                .background(Color.White).fillMaxSize()
                .verticalScroll(scrollState, enabled = isLandscape)
        ) {

            Row(modifier = Modifier.padding(top = 10.dp)) {

                Image(
                    painter = painterResource(R.drawable.backbuttonselected),
                    contentDescription = "back button",
                    modifier = Modifier.size(70.dp)
                        .clickable(onClick = { navHostController.navigate(route = "home")})
                )

                Spacer(modifier = Modifier.width(60.dp))

                Text(
                    text = "BAZE",
                    fontSize = 50.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Make An Announcement",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Course",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Blue,
                modifier = Modifier.padding(horizontal = 30.dp)
            )


            // Dropdown for selecting course

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp)
            ) {
                TextField(
                    value = selectedCourse,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Course", color = Color.Black,)},
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedTrailingIconColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedTrailingIconColor = Color.Black
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded,
                        modifier = Modifier.size(40.dp)) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .border(width = 1.dp,
                            color = Color.Black)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userCourses.forEach { course ->
                        DropdownMenuItem(
                            text = { Text(course, fontSize = 20.sp) },
                            onClick = {
                                selectedCourse = course
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Message",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Blue,
                modifier = Modifier.padding(horizontal = 30.dp)
            )

            // Announcement input
            OutlinedTextField(
                value = announcementText,
                onValueChange = { announcementText = it },
                label = { Text("Announcement Message",
                    color = Color.Black,)},
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp,vertical = 10.dp)
                    .size(200.dp),
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { val facultyName = userProfile?.firstName ?: "Unknown"
                val announcement = hashMapOf(
                    "courseId" to selectedCourse,
                    "message" to announcementText,
                    "facultyName" to facultyName,
                    "timestamp" to System.currentTimeMillis()
                )
                if (selectedCourse.isEmpty() || announcementText.isEmpty()) {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    db.collection("announcements")
                        .add(announcement)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Announcement posted!", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("home")
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                             },
                modifier = Modifier.fillMaxWidth()
                    .padding(40.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue,
                    contentColor = Color.White)
            ) {
                Text(text = "Submit",
                    fontSize = 20.sp)
            }
        }
    }
}