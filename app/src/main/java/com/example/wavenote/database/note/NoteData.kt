package com.example.wavenote.database.note

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wavenote.database.categories.Categories
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.Date
import java.util.UUID

@Parcelize
@Entity
data class NoteData(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val dateCreate: Date = Date(),
    val calendarDay: LocalDate,
    val description: String = "",
    val fileNameAudio: String = "",
    val fileNameImage: String = "",
    val category: String = Categories().nameCategory
) : Parcelable
