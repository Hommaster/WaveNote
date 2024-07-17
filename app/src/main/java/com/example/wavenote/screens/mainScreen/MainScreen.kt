package com.example.wavenote.screens.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.R
import com.example.wavenote.helpers.CustomAppBar
import com.example.wavenote.screens.mainScreen.helper.PulsarFab
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewMainScreen(
    
) {
    MainScreen(paddingValues = PaddingValues(), navController = rememberNavController())
}

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(0.dp, 40.dp, 40.dp, 0.dp),
                drawerContainerColor = colorResource(id = R.color.very_light_brown)
            ) {

            }
        },
        content = {
            Box(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.very_light_brown))
                    .fillMaxSize()
            ) {
                CustomAppBar(
                    navController = navController,
                    onClick = {
                        scope.launch { drawerState.open() }
                    }
                )
                PulsarFab(
                    onClick = {

                    }
                )
            }
        }
    )
}