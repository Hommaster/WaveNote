package com.example.wavenote.database.categories

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
@Entity
data class Categories(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val nameCategory: String = "",
    val colorCategory: Int = 0,
    val dateCreate: Date = Date()
): Parcelable
