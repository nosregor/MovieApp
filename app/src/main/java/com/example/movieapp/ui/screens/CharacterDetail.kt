package com.example.movieapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.movieapp.model.CharactersRepository

@Composable
fun CharacterDetail(repository: CharactersRepository, characterId: Int?) {
    Text(text = "Character Detail: $characterId")
}
