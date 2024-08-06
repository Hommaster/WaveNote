package com.example.wavenote.screens.mainScreen.helper

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.routes.Routes
import java.time.LocalDate


@Composable
fun CardOfNote(
    noteData: NoteData,
    navController: NavHostController
) {

    val limitDescription = 70
    val limitTitle = 15

    val description: String = if(noteData.description.codePointCount(0, noteData.description.length) > limitDescription) {
        noteData.description.substring(0, noteData.description.offsetByCodePoints(0, limitDescription)) + "...."
    } else {
        noteData.description
    }

    val title: String = if(noteData.title.codePointCount(0, noteData.title.length) > limitTitle) {
        noteData.title.substring(0, noteData.title.offsetByCodePoints(0, limitTitle)) + "...."
    } else {
        noteData.title
    }

    Card(
        modifier = Modifier
            .width(150.dp),
        onClick = {
            val localDateString = LocalDate.now().toString()
            val noteDataFromMainScreen = noteData.id.toString()
            navController.navigate(Routes.TextNote.route + "/$localDateString/$noteDataFromMainScreen")
        }
    ) {
        Text(text = title)
        Text(text = description)
    }
}