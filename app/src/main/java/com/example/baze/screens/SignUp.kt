package com.example.baze.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.baze.R
import com.example.baze.viewModel.AuthState
import com.example.baze.viewModel.AuthViewModel


@Composable
fun SignUp(navHostController: NavHostController, authViewModel: AuthViewModel) {

    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current

    var email  by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }
    var mobileNumber  by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("Student") }
    var confirmPassword  by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()


    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.authenticated -> navHostController.navigate(route = "home")
            is AuthState.Error ->  Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Scaffold{ innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)
            .background(Color.White).fillMaxSize()
        .verticalScroll(scrollState, enabled = isLandscape)) {

            Row(modifier = Modifier.padding(top = 10.dp)) {

                Image(
                    painter = painterResource(R.drawable.backbuttonselected),
                    contentDescription = "back button",
                    modifier = Modifier.size(70.dp)
                        .clickable(onClick = {navHostController.navigate(route = "login") })
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
                text = "Sign Up",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp)
            )

            Spacer(modifier = Modifier.size(5.dp))



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                RoleSelection(selectedRole,
                    onRoleSelected = {selectedRole = it})


                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = email,
                    onValueChange = {email= it},
                    label = {Text(text = "Enter username (Email)",
                        color = Color.Black,)},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )

                )


                OutlinedTextField(
                    value = password,
                    onValueChange = {password= it},
                    label = {Text(text = "Enter Password",
                        color = Color.Black,)},
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )


                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {confirmPassword= it},
                    label = {Text(text = "Enter Confirm Password",
                        color = Color.Black,)},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )


                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = {mobileNumber= it},
                    label = {Text(text = "Enter Mobile Number",
                        color = Color.Black,)},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
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

                Button(
                    onClick = {
                        if (email.isNotBlank() && password == confirmPassword) {
                            authViewModel.signup(email, password, mobileNumber, selectedRole)
                        } else {
                            Toast.makeText(
                                context,
                                "Email is empty or passwords do not match",
                                Toast.LENGTH_SHORT
                            ).show()
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
}


@Composable
fun RoleSelection(
    selectedRole: String,
    onRoleSelected: (String) -> Unit
) {
    val roles = listOf("Student", "Faculty")
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("AS:", fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp))
            roles.forEach { role ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50)) // Makes it rounded
                        .border(
                            width = 1.dp,
                            color = if (selectedRole == role) Color.Blue else Color.Gray,
                            shape = RoundedCornerShape(50)
                        )
                        .clickable { onRoleSelected(role) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = selectedRole == role,
                        onClick = { onRoleSelected(role) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Blue
                        )
                    )
                    Text(
                        text = role,
                        modifier = Modifier.padding(start = 4.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}



