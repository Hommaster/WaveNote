package com.example.wavenote.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun NoteDialog(
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        
    }
}