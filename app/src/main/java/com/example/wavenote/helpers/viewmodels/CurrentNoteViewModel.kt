package com.example.wavenote.helpers.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wavenote.database.note.NoteData
import com.example.wavenote.database.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class CurrentNoteViewModel(id: UUID): ViewModel() {

    private val noteRepository = NoteRepository.get()

    private val _note: MutableStateFlow<NoteData?> = MutableStateFlow(null)

    val note: StateFlow<NoteData?> = _note.asStateFlow()

    init {
        viewModelScope.launch {
            _note.value = noteRepository.getCurrentNoteData(id)
        }
    }

    fun updateNote(onUpdate: (NoteData) -> NoteData) {
        _note.update { oldNote ->
            oldNote?.let {
                onUpdate(it)
            }
        }
    }

}