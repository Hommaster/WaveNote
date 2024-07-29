package com.example.wavenote.database.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface NoteDataDao {

    @Query("SELECT * FROM notedata")
    fun getAllNotes(): Flow<List<NoteData>>

    @Query("DELETE FROM notedata")
    suspend fun deleteAll()

    @Query("DELETE FRom notedata WHERE calendarDay=(:calendarDay)")
    suspend fun deleteAllFromLocalDate(calendarDay: LocalDate)

    @Query("DELETE FRom notedata WHERE category=(:category)")
    suspend fun deleteAllFromCategory(category: String)

    @Query("SELECT * FROM notedata WHERE calendarDay=(:calendarDay)")
    suspend fun getAllNotesFromACertainLocalDate(calendarDay: LocalDate): List<NoteData>

    @Query("SELECT * FROM notedata WHERE category=(:category)")
    suspend fun getAllNotesFromACertainCategory(category: String): List<NoteData>

    @Update
    suspend fun updateNoteData(note: NoteData)

    @Insert
    suspend fun addNote(note: NoteData)

    @Delete
    suspend fun deleteNote(note: NoteData)

}