package com.example.wavenote.screens.calendar.data

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize
import java.time.YearMonth

@Parcelize
data class CalendarUiState(
    val yearMonth: YearMonth,
    val dates: List<Date>
) : Parcelable {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val Init = CalendarUiState(
            yearMonth = YearMonth.now(),
            dates = emptyList()
        )
    }
    @Parcelize
    data class Date(
        val dayOfMonth: String,
        var isSelected: Boolean
    ): Parcelable {
        companion object {
            val Empty = Date("", false)
        }
    }
}
