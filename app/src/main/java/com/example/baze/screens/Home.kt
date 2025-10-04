package com.example.baze.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
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
import com.example.baze.viewModel.AuthState
import com.example.baze.viewModel.AuthViewModel


@Composable
fun HomeScreen(navHostController: NavHostController,authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current


    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.unauthenticated -> navHostController.navigate(route = "login")

            else -> Unit
        }
    }
    Scaffold { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
                .background(Color.White).fillMaxSize()
                .verticalScroll(scrollState, enabled = isLandscape)
        ) {

            Row(modifier = Modifier.padding(top = 10.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = "BAZE",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 120.dp)
                )
                Spacer(modifier = Modifier.width(50.dp))

                Column(horizontalAlignment = Alignment.End,){
                    Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout Icon",
                        tint = Color.Black,
                        modifier = Modifier.padding(top = 5.dp)
                            .size(40.dp)
                            .clickable(onClick = {authViewModel.signOut()}))
                    Text(text = "Logout",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }

            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.size(10.dp))


            Text(
                text = " Home",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.Blue,
                modifier = Modifier.padding(20.dp)
            )

            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Row(modifier = Modifier.padding(30.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center) {

                Column {
                    Image(painter = painterResource(R.drawable.announcements),
                        contentDescription = "Announcements",
                        modifier = Modifier.size(120.dp)
                            .clickable(onClick = {navHostController.navigate(route = "announcements")}))

                    Text(text = "Announcements",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(5.dp))
                }

                Spacer(modifier = Modifier.width(60.dp))

                Column {
                    Image(painter = painterResource(R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(120.dp)
                            .clickable(onClick = {navHostController.navigate(route = "profile")}))

                    Text(text = "Profile",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(5.dp)
                            .padding(start = 20.dp))
                }
            }
                Row(modifier = Modifier.padding(30.dp)
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Center) {
                    Column {
                        Image(painter = painterResource(R.drawable.courses),
                            contentDescription = "courses",
                            modifier = Modifier.size(120.dp)
                                .clickable(onClick = {navHostController.navigate(route = "courses")},))

                        Text(text = "Courses",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(5.dp)
                                .padding(start = 30.dp))
                    }

                    Spacer(modifier = Modifier.width(60.dp))

                    Column {
                        Image(painter = painterResource(R.drawable.changepassword),
                            contentDescription = "change password",
                            modifier = Modifier.size(120.dp)
                                .clickable(onClick = {navHostController.navigate(route = "forgotPassword")}))

                        Text(text = "Change Pass",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(5.dp))
                    }

                }

            }







        }
    }
}



