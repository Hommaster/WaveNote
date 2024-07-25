package com.example.wavenote

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.routes.Routes
import com.example.wavenote.screens.calendarNew.CalendarApp
import com.example.wavenote.screens.mainScreen.MainScreen
import com.example.wavenote.screens.notes.textNote.TextNote
import com.example.wavenote.ui.theme.WaveNoteTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            WaveNoteTheme {
                NavHost(navController = navController, startDestination = Routes.Home.route) {
                    composable(Routes.Home.route) {
                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize(),
                            content = { paddingValues ->
                                MainScreen(
                                    navController = navController,
                                    paddingValues = paddingValues
                                )
                            }
                        )
                    }
                    composable(Routes.Calendar.route){
                        CalendarApp()
                    }
                    composable(Routes.TextNote.route){
                        TextNote(navController = navController)
                    }
                }
            }
        }
    }
}

