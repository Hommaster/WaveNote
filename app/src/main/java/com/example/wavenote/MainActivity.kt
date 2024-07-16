package com.example.wavenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.helpers.CustomAppBar
import com.example.wavenote.screens.mainScreen.MainScreen
import com.example.wavenote.ui.theme.WaveNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            WaveNoteTheme {
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen") {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                CustomAppBar()
                            },
                            content = { paddingValues ->
                                MainScreen(paddingValues = paddingValues)
                            }
                        )
                    }
                }
            }
        }
    }
}

