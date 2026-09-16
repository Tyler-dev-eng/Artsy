package com.tylerdev.artshelf.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tylerdev.artshelf.presentation.screens.library.LibraryScreen
import com.tylerdev.artshelf.presentation.screens.search.SearchScreen
import com.tylerdev.artshelf.presentation.screens.splash.SplashScreen

@Suppress("ktlint:standard:function-naming")
@Composable
fun ArtShelfNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentRoute = navController.currentBackStackEntryAsState().value
        ?.destination
        ?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute != Screen.Splash.route) {
                ArtShelfBottomBar(navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onFinished = {
                        navController.navigate(Screen.Search.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(Screen.Search.route) {
                SearchScreen()
            }
            composable(Screen.Library.route) {
                LibraryScreen()
            }
        }
    }
}
