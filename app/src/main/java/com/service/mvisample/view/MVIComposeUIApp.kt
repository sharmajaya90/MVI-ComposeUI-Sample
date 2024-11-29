package com.service.mvisample.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.service.mvisample.view.navigation.Destination
import com.service.mvisample.view.screens.DetailedScreen
import com.service.mvisample.view.screens.LandingScreen
import com.service.mvisample.view.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MVIComposeUIApp(sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()
    MVIComposeNavHost(
        navController = navController,
        sharedViewModel = sharedViewModel
    )
}

@Composable
fun MVIComposeNavHost(navController: NavHostController,sharedViewModel: SharedViewModel) {
    val activity = (LocalContext.current as ComponentActivity)
    val selectedIndexFlow = remember { MutableStateFlow(0) }

    NavHost(navController = navController, startDestination = Destination.home) {
        /*composable(route = Destination.splash) { SplashScreen(navController= navController) }*/
        composable(route = Destination.home) {
            val isInitialized = rememberSaveable { mutableStateOf(false) }

            if (!isInitialized.value) {
                LaunchedEffect(Unit) {
                    isInitialized.value = true
                }
            }


            Box(modifier = Modifier.fillMaxSize()) {
                LandingScreen(
                    onIntent= sharedViewModel::onEvent,
                    uiState=sharedViewModel._state,
                    navController= navController
                )
            }
        }
        composable(route = Destination.detailed) {
            Box(modifier = Modifier.fillMaxSize()) {
                DetailedScreen(
                    uiState=sharedViewModel._state,
                    navController= navController
                )
            }
        }
    }
}

