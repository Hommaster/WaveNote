package com.example.wavenote.screens.calendar

import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wavenote.R
import com.example.wavenote.screens.calendar.data.CalendarUiState
import com.example.wavenote.screens.calendar.util.DateUtil
import com.example.wavenote.screens.calendar.util.getDisplayName
import com.example.wavenote.screens.mainScreen.helper.PulsarFab
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    CalendarApp(
        viewModel = CalendarViewModel()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarApp(
    viewModel: CalendarViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ){ padding ->

        CalendarWidget(
            modifier = Modifier
                .padding(padding),
            days = DateUtil.daysOfWeek,
            yearMonth = uiState.yearMonth,
            dates = uiState.dates,
            onPreviousMonthButtonClicked = { prevMonth ->
                viewModel.toPreviousMonth(prevMonth)
            },
            onNextMonthButtonClicked = { nextMonth ->
                viewModel.toNextMonth(nextMonth)
            },
            onDateClickListener = { date ->
                Log.d("month", "${uiState.yearMonth}")
                date.isSelected = true
                Log.d("dateClick", "$date")
                viewModel.newDate(date)
            }
        )
        PulsarFab(
            onClick = {

            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarWidget(
    modifier: Modifier,
    days: Array<String>,
    yearMonth: YearMonth,
    dates: List<CalendarUiState.Date>,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.very_light_brown))
            .padding(0.dp)
    ) {
        Header(
            yearMonth = yearMonth,
            onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
            onNextMonthButtonClicked = onNextMonthButtonClicked
        )
        Row {
            repeat(days.size) {
                val item = days[it]
                DayItem(item, modifier = Modifier.weight(1f))
            }
        }
        Content(
            dates = dates,
            onDateClickListener = onDateClickListener
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(
    yearMonth: YearMonth,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(0.dp, 0.dp, 13.dp, 13.dp))
            .background(color = Color.Gray, RoundedCornerShape(0.dp, 13.dp, 13.dp, 0.dp)),
        colors = TopAppBarDefaults.topAppBarColors(colorResource(id = R.color.brown_light)),
        title = {
            Row(
                modifier = Modifier
                    .padding(60.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onPreviousMonthButtonClicked.invoke(yearMonth.minusMonths(1))
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "back"
                    )
                }
                Text(
                    text = yearMonth.getDisplayName(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .width(140.dp)
                        .align(Alignment.CenterVertically)
                )
                IconButton(onClick = {
                    onNextMonthButtonClicked.invoke(yearMonth.plusMonths(1))
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "next"
                    )
                }
            }
        }
    )
}

@Composable
fun DayItem(day: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(
    dates: List<CalendarUiState.Date>,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {

    Column {
        var index = 0
        repeat(6) {
            if (index >= dates.size) return@repeat
            Row {
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                    ContentItem(
                        date = item,
                        onClickListener = onDateClickListener,
                        modifier = Modifier.weight(1f)
                    )
                    index++
                }
            }
        }
    }
}

@Composable
fun ContentItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .background(
                color = if (date.isSelected) {
                    colorResource(id = R.color.orange)
                } else {
                    Color.Transparent
                },
                shape = CircleShape
            )
            .clickable {
                onClickListener(date)
            }
    ) {
        Text(
            text = date.dayOfMonth,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}