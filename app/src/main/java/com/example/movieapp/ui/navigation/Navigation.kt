package com.example.movieapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.model.CharactersRepository
import com.example.movieapp.model.datasources.ApiDataSource
import com.example.movieapp.ui.screens.CharacterDetail
import com.example.movieapp.ui.screens.Characters

private const val PARAM_CHARACTER_ID = "characterId"

@Composable
fun Navigation() {

    val navController = rememberNavController()

    val repository = CharactersRepository(ApiDataSource())

    NavHost(
        navController = navController,
        startDestination = Screen.Characters.route
    ) {
        composable(Screen.Characters.route) {
            Characters(repository = repository, navController = navController)
        }
        composable(
            route = Screen.CharacterDetail.route,
            arguments = listOf(
                navArgument(PARAM_CHARACTER_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val convertCharacterIdToString = backStackEntry.arguments?.getString("characterId")
            println("backStackEntry.arguments: $convertCharacterIdToString")
            CharacterDetail(
                repository = repository,
                characterId = backStackEntry.arguments?.getInt(PARAM_CHARACTER_ID),
                onBack = { navController.popBackStack() }
            )
        }
    }
}
