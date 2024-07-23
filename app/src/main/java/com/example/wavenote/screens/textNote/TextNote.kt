package com.example.wavenote.screens.textNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.R
import com.example.wavenote.helpers.BoxWithAdditionalFunctionality
import com.example.wavenote.helpers.BoxWithImageScrollToDismiss
import com.example.wavenote.helpers.SwipeToDismiss

@Preview
@Composable
fun PreviewTextNote() {
    TextNote(
        navController = rememberNavController()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextNote(
    navController: NavHostController
) {
    val message = remember {
        mutableStateOf("")
    }
    SwipeToDismiss(
        navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.very_light_brown))
                .padding(0.dp)
        ) {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 13.dp, 13.dp))
                    .background(
                        color = Color.Gray,
                        RoundedCornerShape(0.dp, 13.dp, 13.dp, 0.dp)
                    ),
                colors = TopAppBarDefaults.topAppBarColors(colorResource(id = R.color.brown_light)),
                title = {
                    BoxWithImageScrollToDismiss(
                        modifier = Modifier
                            .padding(0.dp, 0.dp),
                        colorFilter = R.color.black
                    )
                    BoxWithAdditionalFunctionality(
                        modifier = Modifier
                            .padding(170.dp, 0.dp, 0.dp, 0.dp)
                    )

                }
            )
            TextField(
                modifier = Modifier
                    .padding(10.dp, 4.dp),
                placeholder = {
                    Text(text = "Heading")
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
                modifier = Modifier
                    .padding(10.dp, 4.dp),
                placeholder = {
                    Text(text = "Write here")
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
}