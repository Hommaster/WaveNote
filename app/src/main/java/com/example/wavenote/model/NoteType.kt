package com.example.wavenote.model

import androidx.annotation.StringRes
import com.example.wavenote.R

enum class NoteType(
    @StringRes val nameNoteType: Int
) {

    TextNote(
        nameNoteType = R.string.type_note_text
    )
}