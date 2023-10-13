package com.example.movieapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.model.CharactersRepository
import com.example.movieapp.ui.MainViewModel
import com.example.movieapp.ui.MainViewModel.UiEvent.None
import com.example.movieapp.ui.composables.MyScaffold

@Composable
fun Characters(repository: CharactersRepository, navController: NavController) {

    val viewModel: MainViewModel =
        viewModel { MainViewModel(charactersRepository = repository) }

    val state by viewModel.state.collectAsState()

    val events by viewModel.events.collectAsState(initial = None)

    MyScaffold(
        title = "Characters",
        content = { innerPadding ->
            when (state) {
                is MainViewModel.UiState.Loading -> Loading()
                is MainViewModel.UiState.Success -> Success(
                    viewModel = viewModel,
                    innerPadding = innerPadding,
                    state = state as MainViewModel.UiState.Success
                )

                is MainViewModel.UiState.Error -> Error(state = state as MainViewModel.UiState.Error)
            }
            when (events) {
                is MainViewModel.UiEvent.CharacterSelected -> {
                    val characterId =
                        (events as MainViewModel.UiEvent.CharacterSelected).characterId
                    navController.navigate("characters/$characterId")
                }

                None -> {
                    // do nothing
                }
            }
        }
    )
}

@Composable
private fun Loading() {
    println("Loading()")
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun Success(
    viewModel: MainViewModel, innerPadding: PaddingValues, state: MainViewModel.UiState.Success
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = Modifier.padding(innerPadding)
    ) {
        items(state.data) { character ->
            Text(
                text = character.name,
                color = if (character.favorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable {
                        viewModel.onCharacterClicked(character)
                    }
            )
        }
    }
}

@Composable
private fun Error(state: MainViewModel.UiState.Error) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            state.message,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}
