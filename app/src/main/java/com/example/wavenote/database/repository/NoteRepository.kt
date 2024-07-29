package com.example.wavenote.database.repository

import android.content.Context
import androidx.room.Room
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.database.note.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

class NoteRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){

    private val database: NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        "note_data1"
    ).build()

    fun getNotes(): Flow<List<NoteData>> = database.noteDao().getAllNotes()

    suspend fun deleteAll() = database.noteDao().deleteAll()

    suspend fun getAllNotesFromACertainLocalDate(calendarDay: LocalDate): List<NoteData> = database.noteDao().getAllNotesFromACertainLocalDate(calendarDay)

    suspend fun getAllNotesFromACertainCategory(category: String): List<NoteData> = database.noteDao().getAllNotesFromACertainCategory(category)

    suspend fun deleteAllFromLocalDate(calendarDay: LocalDate) = database.noteDao().deleteAllFromLocalDate(calendarDay)

    suspend fun deleteAllFromCategory(category: String) = database.noteDao().deleteAllFromCategory(category)

    suspend fun updateNoteData(noteData: NoteData) {
        coroutineScope.launch {
            database.noteDao().updateNoteData(noteData)
        }
    }

    suspend fun addNote(noteData: NoteData) = database.noteDao().addNote(noteData)

    suspend fun deleteNote(noteData: NoteData) = database.noteDao().deleteNote(noteData)

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
        }

        fun get(): NoteRepository {
            return INSTANCE ?:
            throw IllegalStateException("NoteRepository must be initialized")
        }
    }
}