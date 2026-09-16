package com.tylerdev.artshelf.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tylerdev.artshelf.presentation.screens.search.SearchScreen

@Suppress("ktlint:standard:function-naming")
@Composable
fun ArtShelfNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route,
        modifier = modifier,
    ) {
        composable(Screen.Search.route) {
            SearchScreen()
        }
    }
}
