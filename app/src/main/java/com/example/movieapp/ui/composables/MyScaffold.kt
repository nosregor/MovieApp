package com.example.movieapp.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(
    title: String,
    content: @Composable (padding: PaddingValues) -> Unit,
    onBackClick: (() -> Unit)? = null,
) {
    MovieAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            color = Color.Blue
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = title) },
                        navigationIcon = {
                            if (onBackClick != null) {
                                IconButton(onClick = onBackClick) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back",
                                    )
                                }
                            }
                        }
                    )
                },
            ) { innerPadding ->
                content(innerPadding)
            }
        }
    }
}
