package com.example.wavenote.database.note

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wavenote.database.categories.Categories
import com.google.gson.Gson
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
    val category: String = Categories().nameCategory,
    val typeNote: String = "",
) : Parcelable


class NoteDataNavType : NavType<NoteData>(isNullableAllowed = true) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(bundle: Bundle, key: String): NoteData? {
        return bundle.getParcelable(key, NoteData::class.java)
    }

    override fun parseValue(value: String): NoteData {
        return Gson().fromJson(value, NoteData::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: NoteData) {
        bundle.putParcelable(key, value)
    }
}
