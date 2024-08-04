package com.example.wavenote.screens.notes.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wavenote.R

@Composable
fun BoxWithImageScrollToDismiss(
    modifier: Modifier,
    colorFilter: Int
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
    ) {
        Image(
            modifier = Modifier
                .size(50.dp),
            colorFilter = ColorFilter.tint(color = colorResource(id = colorFilter)),
            painter = painterResource(id = R.drawable.image_scroll_to_dismiss),
            contentDescription = "image_scroll_to_dismiss"
        )
    }
}