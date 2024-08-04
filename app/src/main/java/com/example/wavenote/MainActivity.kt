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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wavenote.helpers.viewmodels.NotesViewModel
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

        val noteViewModel = NotesViewModel()

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
                                    paddingValues = paddingValues,
                                    noteViewModel = noteViewModel
                                )
                            }
                        )
                    }
                    composable(Routes.Calendar.route){
                        CalendarApp(
                            navController = navController,
                            noteViewModel = noteViewModel
                        )
                    }
                    composable(
                        Routes.TextNote.route + "/{locale_date}",
                        arguments = listOf(
                            navArgument(name = "locale_date"){
                                type = NavType.StringType
                            },
                        )
                    ){ navBackStackEntry ->
                        TextNote(
                            navController = navController,
                            noteViewModel = noteViewModel,
                            localeDateString = navBackStackEntry.arguments?.getString("locale_date")
                        )
                    }
                }
            }
        }
    }
}

