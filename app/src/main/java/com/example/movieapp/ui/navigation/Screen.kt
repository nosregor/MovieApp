package com.example.movieapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Characters : Screen("characters")
    object CharacterDetail : Screen("characters/{characterId}")
}
