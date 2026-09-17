package com.tylerdev.artshelf.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tylerdev.artshelf.presentation.screens.artworkdetail.ArtworkDetailScreen
import com.tylerdev.artshelf.presentation.screens.library.LibraryScreen
import com.tylerdev.artshelf.presentation.screens.search.SearchScreen
import com.tylerdev.artshelf.presentation.screens.splash.SplashScreen

private val ARTWORK_DETAIL_ARGUMENTS =
    listOf(
        navArgument("id") { type = NavType.StringType },
        navArgument("pageUrl") { type = NavType.StringType },
        navArgument("previewUrl") { type = NavType.StringType },
        navArgument("webformatUrl") { type = NavType.StringType },
        navArgument("largeImageUrl") { type = NavType.StringType },
        navArgument("tags") { type = NavType.StringType },
        navArgument("userName") { type = NavType.StringType },
        navArgument("likes") { type = NavType.StringType },
        navArgument("downloads") { type = NavType.StringType },
    )

@Suppress("ktlint:standard:function-naming")
@Composable
fun ArtShelfNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentRoute = navController.currentBackStackEntryAsState().value
        ?.destination
        ?.route

    val isBottomBarVisible = currentRoute != Screen.Splash.route && currentRoute != Screen.ArtworkDetail.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (isBottomBarVisible) {
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
                SearchScreen(
                    onArtClick = { art -> navController.navigate(Screen.ArtworkDetail.createRoute(art)) },
                )
            }
            composable(Screen.Library.route) {
                LibraryScreen(
                    onExploreClick = {
                        navController.navigate(Screen.Search.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(Screen.ArtworkDetail.route, arguments = ARTWORK_DETAIL_ARGUMENTS) {
                ArtworkDetailScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}
