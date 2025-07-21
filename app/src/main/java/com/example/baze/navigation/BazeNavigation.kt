package com.example.baze.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baze.screens.Announcements
import com.example.baze.screens.CourseScreen
import com.example.baze.screens.ForgotPassword
import com.example.baze.screens.HomeScreen
import com.example.baze.screens.LoginScreen
import com.example.baze.screens.MakeAnnouncements
import com.example.baze.screens.ProfileScreen
import com.example.baze.screens.SignUp
import com.example.baze.viewModel.AuthViewModel

@Composable
fun BazeNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel){

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = "login"){

        composable(route = "home") {
            HomeScreen(
                navHostController,
                authViewModel)
        }

        composable(route = "signUp") {
            SignUp(navHostController, authViewModel)
        }
        composable(route = "login") {
            LoginScreen(navHostController, authViewModel)
        }
        composable(route = "forgotPassword") {
            ForgotPassword(navHostController,authViewModel)
        }
        composable(route = "profile") {
            ProfileScreen(navHostController)
        }
        composable(route = "makeAnnouncement") {
            MakeAnnouncements(navHostController,authViewModel)
        }
        composable(route = "courses") {
            CourseScreen(navHostController)
        }
        composable(route = "announcements") {
            Announcements(authViewModel,navHostController)
        }

    }
}

