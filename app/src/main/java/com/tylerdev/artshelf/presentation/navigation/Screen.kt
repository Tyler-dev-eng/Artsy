package com.tylerdev.artshelf.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Search : Screen("search")
    data object Library : Screen("library")
}
