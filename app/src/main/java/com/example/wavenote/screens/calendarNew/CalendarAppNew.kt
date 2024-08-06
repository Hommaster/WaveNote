package com.example.wavenote.screens.calendarNew

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.R
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.helpers.NoteDialog
import com.example.wavenote.helpers.PulsarFab
import com.example.wavenote.helpers.viewmodels.NotesViewModel
import com.example.wavenote.routes.Routes
import com.example.wavenote.screens.mainScreen.helper.CardOfNote
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import java.time.Month as Month

@Preview
@Composable
fun PreviewCalendarNewApp(){
    CalendarApp(
        noteViewModel = NotesViewModel(),
        navController = rememberNavController()
    )
}


@Composable
fun CalendarApp(
    noteViewModel: NotesViewModel,
    navController: NavHostController
) {

    val scope = rememberCoroutineScope()

    val notesList : MutableState<List<NoteData>> = rememberSaveable {
        mutableStateOf(emptyList())
    }

    val calendarLocaleDate : MutableState<LocalDate> = rememberSaveable {
        mutableStateOf(LocalDate.now())
    }

    var dialogCall by rememberSaveable {
        mutableStateOf(false)
    }

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed

    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            notesList.value =
                noteViewModel.getAllNotesFromACertainLocalDate(calendarDay = calendarLocaleDate.value)
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.very_light_brown))
        ) {
            HorizontalCalendar(
                state = state,
                dayContent = { day ->
                    Day(day, isSelected = selectedDate == day.date, scope, noteViewModel) { calendarDay ->
                        selectedDate =
                            if (selectedDate == calendarDay.date) null else calendarDay.date
                        calendarLocaleDate.value = calendarDay.date
                        scope.launch {
                            notesList.value =
                                noteViewModel.getAllNotesFromACertainLocalDate(calendarDay = calendarLocaleDate.value)
                        }
                    }
                },
                monthHeader = { month ->
                    MonthAndYearTitle(
                        monthOfYear = Month.of(month.yearMonth.monthValue)
                            .getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()),
                        year = month.yearMonth.year.toString()
                    )
                    DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                },
            )
            rememberLazyStaggeredGridState().also {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(15.dp, 100.dp)
                        .fillMaxSize(),
                    state = it,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalItemSpacing = 10.dp,
                    content = {
                        notesList.value.forEach { noteData ->
                            item {
                                CardOfNote(noteData = noteData, navController = navController)
                            }
                        }
                    }
                )
            }
        }
        Box {
            PulsarFab(
                onClick = {
                    dialogCall = true
                }
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 35.dp)
        ) {
            IconButton(onClick = {
                navController.popBackStack(Routes.Home.route, false)
            }) {
                Icon(
                    modifier = Modifier
                        .size(34.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                    contentDescription = "ArrowBack"
                )
            }
        }
        if(dialogCall) {
            NoteDialog(
                localeDate = calendarLocaleDate.value,
                onDismissRequest = { dialogCall = false },
                onRequest = { dialogCall = false},
                navController = navController
            )
        }

    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    scope: CoroutineScope,
    noteViewModel: NotesViewModel,
    onClick: (CalendarDay) -> Unit
    ) {

    val noteListOfThisDateIsNotEmpty : MutableState<Boolean> = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            if (noteViewModel.getAllNotesFromACertainLocalDate(calendarDay = day.date).isNotEmpty()) {
                noteListOfThisDateIsNotEmpty.value = true
            }
        }
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = if (isSelected) colorResource(id = R.color.orange) else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = {
                    onClick(day)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) Color.White else Color.Gray
            )
            if (noteListOfThisDateIsNotEmpty.value) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(10.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = "noteListNotEmpty"
                    )
                }
            }
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthAndYearTitle(
    monthOfYear: String,
    year: String
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    fontSize = 27.sp,
                    textAlign = TextAlign.Center,
                    text = "${monthOfYear.uppercase()}  $year"
                )
            }
        }
    )
}
