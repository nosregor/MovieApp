package com.example.movieapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.ui.MainViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.usecases.FilterCharactersByLastNameUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(filterUseCase: FilterCharactersByLastNameUseCase) {
    val viewModel: MainViewModel =
        viewModel { MainViewModel(filterCharactersUseCase = filterUseCase) }
    val state by viewModel.state.collectAsState()

    MovieAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = "Rick and Morty") }) }
            ) { innerPadding ->
                when (state) {
                    is MainViewModel.UiState.Initial -> ButtonForCharacters(fetchCharacters = { viewModel.onButtonClicked() })
                    is MainViewModel.UiState.Loading -> Loading()
                    is MainViewModel.UiState.Success -> Success(
                        viewModel = viewModel,
                        innerPadding = innerPadding,
                        state = state as MainViewModel.UiState.Success
                    )

                    is MainViewModel.UiState.Error -> Error(state = state as MainViewModel.UiState.Error)
                }
            }
        }
    }
}

@Preview
@Composable
fun ButtonForCharacters(fetchCharacters: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = fetchCharacters
        ) {
            Text("Fetch", fontSize = 24.sp)
        }
    }
}

@Composable
private fun Loading() {
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
