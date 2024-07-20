package com.example.wavenote.screens.textNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wavenote.R

@Preview
@Composable
fun PreviewTextNote() {
    TextNote()
}

@Composable
fun TextNote(

) {
    val message = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.very_light_brown))
            .padding(0.dp)
    ) {
        TextField(
            placeholder = {
                Text(text = "Enter your label")
            },
            value = message.value,
            onValueChange = { text ->
                message.value = text
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xffeeeeee),
                unfocusedTextColor = Color(0xff888888),
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color(0xff222222),
            )
        )
        TextField(
            placeholder = {
                Text(text = "Enter your label")
            },
            value = message.value,
            onValueChange = { text ->
                message.value = text
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xffeeeeee),
                unfocusedTextColor = Color(0xff888888),
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color(0xff222222),
            )
        )
    }

}