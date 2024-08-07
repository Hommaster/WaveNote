package com.example.wavenote.screens.notes.textNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wavenote.R
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.screens.notes.helpers.BoxWithAdditionalFunctionality
import com.example.wavenote.screens.notes.helpers.BoxWithImageScrollToDismiss
import com.example.wavenote.helpers.SwipeToDismiss
import com.example.wavenote.helpers.viewmodels.CurrentNoteViewModel
import com.example.wavenote.helpers.viewmodels.NotesViewModel
import com.example.wavenote.routes.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextNote(
    navController: NavHostController,
    noteViewModel: NotesViewModel,
    localeDateString: String?,
    noteData: String?
) {

    val coroutineScope = rememberCoroutineScope()

    val note: MutableState<NoteData?> = rememberSaveable {
        mutableStateOf(null)
    }

    val currentNoteViewModel = if(noteData != "1") CurrentNoteViewModel(UUID.fromString(noteData)) else null


    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }

    val localeDate = LocalDate.parse(localeDateString)


    SwipeToDismiss(
        navController
    ) {
        LaunchedEffect(key1 = Unit) {
            delay(70)
            note.value = currentNoteViewModel?.note?.value
            coroutineScope.launch {
                title.value = note.value?.title ?: ""
                description.value = note.value?.description ?: ""
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.very_light_brown))
                .padding(0.dp)
        ) {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 13.dp, 13.dp))
                    .background(
                        color = Color.Gray,
                        RoundedCornerShape(0.dp, 13.dp, 13.dp, 0.dp)
                    ),
                colors = TopAppBarDefaults.topAppBarColors(colorResource(id = R.color.brown_light)),
                title = {
                    BoxWithImageScrollToDismiss(
                        modifier = Modifier
                            .padding(0.dp, 0.dp),
                        colorFilter = R.color.black
                    )
                    BoxWithAdditionalFunctionality(
                        modifier = Modifier
                            .padding(170.dp, 0.dp, 0.dp, 0.dp)
                    )

                }
            )
            TextField(
                modifier = Modifier
                    .padding(10.dp, 4.dp),
                placeholder = {
                    Text(text = "Heading")
                },
                value = title.value,
                onValueChange = { text ->
                    title.value = text
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xffeeeeee),
                    unfocusedTextColor = Color(0xff888888),
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = Color(0xff222222),
                )
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {
                item {
                    TextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp, 4.dp),
                        placeholder = {
                            Text(text = "Write here")
                        },
                        value = description.value,
                        onValueChange = { text ->
                            description.value = text
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xffeeeeee),
                            unfocusedTextColor = Color(0xff888888),
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color(0xff222222),
                        )
                    )
                }
            }
            Row {
                Text(text = "this need icons place")
                IconButton(onClick = {
                    if(currentNoteViewModel != null) {
                        coroutineScope.launch {
                            currentNoteViewModel.updateNote { oldNote ->
                                oldNote.copy(
                                    id = currentNoteViewModel.note.value!!.id,
                                    title = title.value,
                                    description = description.value,
                                    calendarDay = currentNoteViewModel.note.value!!.calendarDay,
                                    dateCreate = currentNoteViewModel.note.value!!.dateCreate,
                                    fileNameAudio = currentNoteViewModel.note.value!!.fileNameAudio,
                                    fileNameImage = currentNoteViewModel.note.value!!.fileNameImage,
                                    category = currentNoteViewModel.note.value!!.category,
                                    typeNote = currentNoteViewModel.note.value!!.typeNote
                                )
                            }
                        }
                        coroutineScope.launch {
                            note.value = currentNoteViewModel.note.value
                        }
                        coroutineScope.launch {
                            noteViewModel.updateNoteData(note.value!!)
                        }
                    } else {
                        val newNoteData = NoteData(
                            title = title.value,
                            description = description.value,
                            calendarDay = localeDate,
                            category = "Test"
                        )
                        coroutineScope.launch {
                            noteViewModel.addNote(newNoteData)
                        }
                    }
                    coroutineScope.launch {
                        navController.popBackStack(Routes.Home.route, false)
                    }
                }) {
                    Icon(
                        Icons.Default.Add,
                        "Add"
                    )
                }

            }
        }
    }
}