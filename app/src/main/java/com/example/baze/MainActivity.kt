package com.example.baze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.navigation.compose.rememberNavController
import com.example.baze.navigation.BazeNavigation
import com.example.baze.ui.theme.BAZETheme
import com.example.baze.viewModel.AuthViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel : AuthViewModel by viewModels()
        setContent {
            BAZETheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BazeNavigation(modifier = Modifier.padding(innerPadding),
                        authViewModel)
                }
            }
        }
    }
}

