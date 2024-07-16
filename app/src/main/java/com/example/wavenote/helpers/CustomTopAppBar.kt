package com.example.wavenote.helpers

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(

) {
    TopAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(13.dp))
            .background(color = Color.Gray, RoundedCornerShape(13.dp)),
        colors = TopAppBarDefaults.topAppBarColors(Color.Gray),
        title = {
            Text(
                text = "All notes"
            )
        }
    )
}