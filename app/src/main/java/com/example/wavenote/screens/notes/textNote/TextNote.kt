package com.example.wavenote.screens.notes.textNote

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.R
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.screens.notes.helpers.BoxWithAdditionalFunctionality
import com.example.wavenote.screens.notes.helpers.BoxWithImageScrollToDismiss
import com.example.wavenote.helpers.SwipeToDismiss
import com.example.wavenote.helpers.viewmodels.NotesViewModel
import com.example.wavenote.routes.Routes
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewTextNote() {
    TextNote(
        navController = rememberNavController(),
        noteViewModel = NotesViewModel()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextNote(
    navController: NavHostController,
    noteViewModel: NotesViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    SwipeToDismiss(
        navController
    ) {
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

                    val newNoteData = NoteData(
                        title = title.value,
                        description = description.value,
                        calendarDay = LocalDate.now(),
                        category = "Test"
                    )
                    coroutineScope.launch {
                        noteViewModel.addNote(newNoteData)
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