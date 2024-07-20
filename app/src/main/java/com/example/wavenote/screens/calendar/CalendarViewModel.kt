package com.example.wavenote.screens.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavenote.screens.calendar.data.CalendarDataSource
import com.example.wavenote.screens.calendar.data.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel : ViewModel() {

    private val dataSource by lazy { CalendarDataSource() }

    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState: MutableStateFlow<CalendarUiState> = MutableStateFlow(CalendarUiState.Init)
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    dates = dataSource.getDates(currentState.yearMonth)
                )
            }
            Log.d("101", "010")
        }
    }

    fun toNextMonth(nextMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    yearMonth = nextMonth,
                    dates = dataSource.getDates(nextMonth)
                )
            }
        }
    }

    fun toPreviousMonth(prevMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    yearMonth = prevMonth,
                    dates = dataSource.getDates(prevMonth)
                )
            }
        }
    }

    fun newDate(date: CalendarUiState.Date) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    yearMonth = currentState.yearMonth,
                    dates = dataSource.getNewDates(currentState.yearMonth, date)
                )
            }
        }
    }



}