package com.example.wavenote.routes

sealed class Routes(val route: String) {

    data object Home : Routes("home")
    data object Calendar : Routes("calendar")
    data object TextNote : Routes("text_note")

}