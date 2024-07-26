package com.example.wavenote.screens.notes.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wavenote.R

@Preview
@Composable
fun PreviewBWAF(){
    BoxWithAdditionalFunctionality(Modifier)
}


@Composable
fun BoxWithAdditionalFunctionality(modifier: Modifier) {
    val size = 30.dp
    Row(
        modifier = modifier
            .padding(0.dp, 15.dp)
    ){
        Box(
            modifier = Modifier
                .padding(10.dp, 0.dp)
                .background(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .size(size),
                painter = painterResource(id = R.drawable.speech),
                contentDescription = "image_scroll_to_dismiss"
            )
        }
        Box(
            modifier = Modifier
                .padding(12.dp, 0.dp)
                .background(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .size(size),
                painter = painterResource(id = R.drawable.bookmark),
                contentDescription = "image_scroll_to_dismiss"
            )
        }
        Box(
            modifier = Modifier
                .padding(14.dp, 0.dp)
                .background(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .size(size),
                painter = painterResource(id = R.drawable.alarm),
                contentDescription = "image_scroll_to_dismiss"
            )
        }
        Box(
            modifier = Modifier
                .padding(6.dp, 0.dp)
                .background(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .size(size),
                painter = painterResource(id = R.drawable.dots),
                contentDescription = "image_scroll_to_dismiss"
            )
        }
    }
}