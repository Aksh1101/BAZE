package com.example.baze.screens

import android.content.res.Configuration
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.baze.viewModel.AuthViewModel


@Composable
fun ForgotPassword(navHostController: NavHostController, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    Scaffold(
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)
            .background(Color.White).fillMaxSize()
            .verticalScroll(scrollState, enabled = isLandscape)) {

            Row(modifier = Modifier.padding(top = 10.dp)) {

                Image(painter = painterResource(R.drawable.backbuttonselected),
                    contentDescription =  "back button",
                    modifier = Modifier.size(70.dp)
                        .clickable(onClick = {navHostController.navigate(route = "login")}))

                Spacer(modifier = Modifier.width(60.dp))

                Text(text = "BAZE",
                    fontSize = 50.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)

            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.size(10.dp))


            Text(text = "Forgot Password",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp))

            Spacer(modifier = Modifier.size(10.dp))

            var email  by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = {email= it},
                label = {Text(text = "Enter username (Email)",
                    color = Color.Black,)},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
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
            
            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = { },
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