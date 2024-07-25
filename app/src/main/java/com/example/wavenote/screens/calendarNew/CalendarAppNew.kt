package com.example.wavenote.screens.calendarNew

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavenote.R
import com.example.wavenote.helpers.PulsarFab
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewCalendarNewApp(){
    CalendarApp()
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarApp(

) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed

    val daysOfWeek = remember { daysOfWeek() }

    var monthOfYear = rememberSaveable {
        mutableStateOf("")
    }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.very_light_brown))
    ){
        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(day, isSelected = selectedDate == day.date) { calendarDay ->
                    selectedDate = if (selectedDate == calendarDay.date) null else calendarDay.date
                }
            },
            monthHeader = { month ->
                monthOfYear.value = month.yearMonth.month.toString()
                MonthAndYearTitle(monthOfYear = month.yearMonth.month.toString(), year = month.yearMonth.year.toString())
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            },
        )
        Box(
            modifier = Modifier
                .padding(268.dp, 297.dp, 0.dp, 0.dp),
        ) {
            PulsarFab(
                onClick = {

                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
    ) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = if (isSelected) colorResource(id = R.color.orange) else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = {
                    onClick(day)
                    val dateString = day.date
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if(day.position == DayPosition.MonthDate) Color.White else Color.Gray
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
@RequiresApi(Build.VERSION_CODES.O)
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
                    text = "$monthOfYear  $year"
                )
            }
        }
    )
}
