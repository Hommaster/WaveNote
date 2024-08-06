package com.example.wavenote.screens.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.R
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.helpers.CustomAppBar
import com.example.wavenote.helpers.NoteDialog
import com.example.wavenote.helpers.PulsarFab
import com.example.wavenote.helpers.viewmodels.NotesViewModel
import com.example.wavenote.screens.mainScreen.helper.CardOfNote
import kotlinx.coroutines.launch
import java.time.LocalDate

@Preview
@Composable
fun PreviewMainScreen(
    
) {
    MainScreen(
        paddingValues = PaddingValues(),
        navController = rememberNavController(),
        noteViewModel = NotesViewModel()
    )
}

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    noteViewModel: NotesViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var dialogCall by rememberSaveable {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    val notesList : MutableState<List<NoteData>> = rememberSaveable {
        mutableStateOf(emptyList())
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(0.dp, 40.dp, 40.dp, 0.dp),
                drawerContainerColor = colorResource(id = R.color.very_light_brown)
            ) {

            }
        },
        content = {
            Box(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.very_light_brown))
                    .fillMaxSize()
            ) {
                CustomAppBar(
                    navController = navController,
                    onClick = {
                        scope.launch { drawerState.open() }
                    }
                )

                val state = rememberLazyStaggeredGridState()

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(15.dp, 100.dp)
                        .fillMaxSize(),
                    state = state,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalItemSpacing = 10.dp,
                    content = {

                        coroutineScope.launch {
                            noteViewModel.notes.collect{
                                notesList.value = it
                            }
                        }
                        notesList.value.forEach { noteData ->
                            item {
                                CardOfNote(
                                    navController = navController,
                                    noteData = noteData
                                )
                            }
                        }

                    }
                )
                PulsarFab(
                    onClick = { dialogCall = true }
                )

            }
            if(dialogCall) {
                NoteDialog(
                    navController = navController,
                    localeDate = LocalDate.now(),
                    onDismissRequest = {
                        dialogCall = false
                    },
                    onRequest = { dialogCall = false},
                )
            }
        }
    )
}