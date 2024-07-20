package com.example.wavenote.screens.calendar.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wavenote.screens.calendar.util.getDayOfMonthStartingFromMonday
import java.time.LocalDate
import java.time.YearMonth

class CalendarDataSource {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDates(yearMonth: YearMonth): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                CalendarUiState.Date(
                    dayOfMonth = if (date.monthValue == yearMonth.monthValue) {
                        "${date.dayOfMonth}"
                    } else {
                        "" // Fill with empty string for days outside the current month
                    },
                    isSelected = if(LocalDate.now().monthValue != date.monthValue || LocalDate.now().year != date.year) date.dayOfMonth == 1 else date.isEqual(LocalDate.now()) && date.monthValue == yearMonth.monthValue
                )
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNewDates(yearMonth: YearMonth, dateNew: CalendarUiState.Date): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                CalendarUiState.Date(
                    dayOfMonth = if (date.monthValue == yearMonth.monthValue) {
                        "${date.dayOfMonth}"
                    } else {
                        ""
                    },
                    isSelected = dateNew.dayOfMonth.toInt() == date.dayOfMonth && date.monthValue == yearMonth.monthValue
                )
            }
    }
}