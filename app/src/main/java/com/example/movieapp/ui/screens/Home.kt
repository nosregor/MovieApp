package com.example.movieapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.model.CharactersRepository
import com.example.movieapp.ui.MainViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.Pink40

//fun Home(filterUseCase: FilterCharactersByLastNameUseCase) {
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(repository: CharactersRepository, navController: NavController) {
    val viewModel: MainViewModel =
        viewModel { MainViewModel(charactersRepository = repository) }
    val state by viewModel.state.collectAsState()

    MovieAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            color = Color.Blue
//            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Rick and Morty Characters") },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Pink40
                        )
                    )
                }
            ) {
                ButtonForCharacters(fetchCharacters = {
                    viewModel.onButtonClicked(
                        repository
                    )
                })
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
