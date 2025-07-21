package com.example.baze.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.baze.R
import com.example.baze.data.FirestoreRepository
import com.example.baze.data.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ProfileScreen(navHostController: NavHostController) {

    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()
    val repository = remember { FirestoreRepository() }


// State for profile fields
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        repository.getUserProfile(
            onSuccess = {
                email = it.email
                mobileNumber = it.mobileNumber
                role = it.role
                firstName = it.firstName
                lastName = it.lastName
            },
            onFailure = {
                Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        )
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
                        .clickable(onClick = {navHostController.navigate(route = "home") })
                )

                Spacer(modifier = Modifier.width(60.dp))

                Text(
                    text = "BAZE",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Profile",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp)
            )



            OutlinedTextField(
                value = email,
                onValueChange = {email= it},
                label = {Text(text = "Enter username (Email)")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )

            )

            OutlinedTextField(
                value = firstName,
                onValueChange = {firstName= it},
                label = {Text(text = "Enter First Name ")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )

            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {lastName= it},
                label = {Text(text = "Enter Last Name")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )

            )

            OutlinedTextField(
                value = mobileNumber,
                onValueChange = {mobileNumber= it},
                label = {Text(text = "Enter Mobile Number")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )

            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                val profile = UserProfile(
                    email = email,
                    mobileNumber = mobileNumber,
                    role = role,
                    firstName = firstName,
                    lastName = lastName
                )
                repository.saveUserProfile(
                    profile = profile,
                    onSuccess = {
                        Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(route = "home")
                    },
                    onFailure = {
                        Toast.makeText(context, "Failed to save profile: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                )
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