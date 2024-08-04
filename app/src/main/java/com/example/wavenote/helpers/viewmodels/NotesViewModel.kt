package com.example.wavenote.helpers.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.database.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class NotesViewModel : ViewModel() {

    private val noteRepository = NoteRepository.get()

    private val _notes: MutableStateFlow<List<NoteData>> = MutableStateFlow(emptyList())

    val notes: StateFlow<List<NoteData>>
        get() = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.getNotes().collect{
                _notes.value = it
            }
        }
    }

    fun getNotes() {
        noteRepository.getNotes()
    }

    suspend fun addNote(noteData: NoteData) {
        noteRepository.addNote(noteData)
    }

    suspend fun deleteNote(noteData: NoteData) {
        noteRepository.deleteNote(noteData)
    }

    suspend fun deleteAllNotes(noteData: NoteData) {
        noteRepository.deleteAll()
    }

    suspend fun getAllNotesFromACertainLocalDate(calendarDay: LocalDate) : List<NoteData> {
        return noteRepository.getAllNotesFromACertainLocalDate(calendarDay)
    }

    suspend fun getAllNotesFromACertainCategory(category: String) {
        noteRepository.getAllNotesFromACertainCategory(category)
    }

    suspend fun deleteAllFromLocalDate(calendarDay: LocalDate) {
        noteRepository.deleteAllFromLocalDate(calendarDay)
    }

    suspend fun deleteAllFromCategory(category: String) {
        noteRepository.deleteAllFromCategory(category)
    }

    suspend fun updateNoteData(noteData: NoteData) {
        noteRepository.updateNoteData(noteData)
    }

}