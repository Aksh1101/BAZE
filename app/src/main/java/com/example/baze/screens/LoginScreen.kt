package com.example.baze.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.baze.R
import com.example.baze.viewModel.AuthState
import com.example.baze.viewModel.AuthViewModel


@Composable
fun LoginScreen(navHostController: NavHostController ,authViewModel: AuthViewModel) {

    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var email by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current


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

    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight()
                .fillMaxSize())


        Column(modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState, enabled = isLandscape)
            .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.padding(top = 100.dp)
                    .size(140.dp)
                    .padding(24.dp)
                    )

            Text(text = "BAZE",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(text = "Student Notification System",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(32.dp))


            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text(text = "Email")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
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
                value = password,
                onValueChange = {password = it},
                label = {Text(text = "Password")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
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

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                authViewModel.login(email,password)
            },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Black,
                    contentColor = Color.Black)
                ) {
                Text(text = "Login",
                    fontSize = 20.sp,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navHostController.navigate(route = "signUp")

            },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Black,
                    contentColor = Color.Black)
            ) {
                Text(text = "Sign Up",
                    fontSize = 20.sp,
                    color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            // add forget password

            Text(text = "Forgot Password",
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = {navHostController.navigate(route = "forgotPassword")}))
        }
    }
}
