package com.tylerdev.artshelf.presentation.navigation

sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object Library : Screen("library")
}
