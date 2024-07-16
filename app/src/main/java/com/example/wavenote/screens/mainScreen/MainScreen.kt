package com.example.wavenote.screens.mainScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    paddingValues: PaddingValues
) {
    Text(
        modifier = Modifier
            .padding(paddingValues),
        text = "Hello word!"
    )
}