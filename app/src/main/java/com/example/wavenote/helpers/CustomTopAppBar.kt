package com.example.wavenote.helpers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.R
import com.example.wavenote.routes.Routes

@Preview
@Composable
fun PreviewCustomTopAppBar() {
    CustomAppBar(
        navController = rememberNavController(),
        onClick = {

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    navController: NavController,
    onClick: () -> Unit
) {
    val navigate = rememberNavController()
    TopAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(0.dp, 0.dp, 13.dp, 13.dp))
            .background(color = Color.Gray, RoundedCornerShape(0.dp, 13.dp, 13.dp, 0.dp)),
        colors = TopAppBarDefaults.topAppBarColors(colorResource(id = R.color.brown_light)),
        title = {
            Row{
                Row(
                    modifier = Modifier
                        .width(270.dp)
                ){
                    IconButton(
                        onClick = { onClick() },
                        content = { Icon(Icons.Filled.Menu, "Menu") }
                    )
                    Text(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = "All notes" //max 18 symbols
                    )
                }
                Row {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(Icons.Filled.Search, "Search")
                    }
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.Calendar.route)
                        }
                    ) {
                        Icon(Icons.Filled.DateRange, "DateRange")
                    }
                }
            }

        }
    )
}